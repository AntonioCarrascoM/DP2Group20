
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import security.Authority;
import domain.Actor;
import domain.Box;
import domain.Brotherhood;
import domain.Configuration;
import domain.Enrolment;
import domain.Member;
import domain.Message;
import domain.Parade;
import domain.Request;

@Service
@Transactional
public class MessageService {

	//Managed repository --------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	//Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods --------------------------------

	public Message create() {

		final Message message = new Message();

		message.setSender(this.actorService.findByPrincipal());
		message.setSent(new Date(System.currentTimeMillis() - 1));
		message.setSpam(false);
		final Collection<Box> boxList = new ArrayList<Box>();
		final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Out box");
		boxList.add(box);
		message.setBoxes(boxList);

		return message;
	}

	public Collection<Message> findAll() {

		return this.messageRepository.findAll();
	}

	public Message findOne(final int id) {
		Assert.notNull(id);

		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		Assert.notNull(message);

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == message.getSender().getId() || this.actorService.findByPrincipal().getId() == message.getRecipient().getId());

		if (message.getId() == 0)
			message.setSent(new Date(System.currentTimeMillis() - 1));

		final Boolean spamBody = this.checkSpam(message.getBody());
		final Boolean spamSubject = this.checkSpam(message.getSubject());
		final Boolean spamTags = this.checkSpam(message.getTags());

		//Sets message as spam if any of those parts contains a spam word.
		if (spamBody == true || spamSubject == true || spamTags == true)
			message.setSpam(true);

		final Message saved = this.messageRepository.save(message);

		return saved;
	}

	public void delete(final Message message) {
		Assert.notNull(message);

		//Assertion that the user deleting this message has the correct privileges.
		final Collection<Box> boxes = this.boxService.getBoxesByMessageAndActor(this.actorService.findByPrincipal().getId(), message.getId());
		//Assert.isTrue(this.actorService.findByPrincipal().getId() == boxes.getId());

		if (!boxes.contains(this.boxService.getBoxByName(this.actorService.findByPrincipal().getId(), "Trash box"))) {
			final Box b = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Trash box");
			this.move(message, b);

		} else {

			//Removing message from TrashBox
			final Box trash = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Trash box");
			final Collection<Message> trashMessages = trash.getMessages();
			trashMessages.remove(message);
			this.boxService.saveFromMessage(trash);

			if (message.getBoxes().size() == 1)
				this.messageRepository.delete(message);
			else {
				//Saving message without Trash box
				final Collection<Box> newBoxes = message.getBoxes();
				newBoxes.remove(trash);
				message.setBoxes(newBoxes);
				this.save(message);
			}
		}
	}

	//Other business methods ----------------------------

	public void send(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		//Parsing the message's subject and body for spam words.
		final boolean isSpamSubject = this.checkSpam(m.getSubject());
		final boolean isSpamBody = this.checkSpam(m.getBody());
		String boxName = "In box";
		if (isSpamSubject == true || isSpamBody == true)
			boxName = "Spam box";

		//Finds the system folder where the message must be sent to.
		final Box b = this.boxService.getSystemBoxByName(a.getId(), boxName);
		final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Out box");

		m.setRecipient(a);
		m.getBoxes().add(b);
		final Message saved = this.save(m);

		//Saving Inbox recipient box with the new message
		final Collection<Message> messagesOfRecipientInbox = b.getMessages();
		messagesOfRecipientInbox.add(saved);
		b.setMessages(messagesOfRecipientInbox);
		this.boxService.saveFromMessage(b);

		//Saving Outbox sender box with the new message
		final Collection<Message> messagesOfSenderOutbox = box.getMessages();
		messagesOfSenderOutbox.add(saved);
		box.setMessages(messagesOfSenderOutbox);
		this.boxService.saveFromMessage(box);

		if (this.actorService.isBannable(m.getSender()) == true) {
			m.getSender().setSpammer(true);
			this.actorService.save(m.getSender());
		}

	}

	//Moves a message from one folder to another.

	public void move(final Message message, final Box newOne) {
		Assert.notNull(message);
		Assert.notNull(newOne);

		final Collection<Box> oldBoxes = this.boxService.getBoxesByMessageAndActor(this.actorService.findByPrincipal().getId(), message.getId());
		final Collection<Box> messageBoxes = message.getBoxes();

		for (final Box oldBox : oldBoxes) {

			//Deleting message from old boxes
			final Collection<Message> messagesFromOldBox = oldBox.getMessages();
			messagesFromOldBox.remove(message);
			oldBox.setMessages(messagesFromOldBox);
			this.boxService.saveFromMessage(oldBox);

			//Deleting old boxes
			messageBoxes.remove(oldBox);
			message.setBoxes(messageBoxes);
			this.save(message);
		}

		//Inserting new message to new box and saving
		final Collection<Message> messagesFromNewOne = newOne.getMessages();
		messagesFromNewOne.add(message);
		newOne.setMessages(messagesFromNewOne);
		this.boxService.saveFromMessage(newOne);

		//Inserting new box to new message and saving
		messageBoxes.add(newOne);
		message.setBoxes(messageBoxes);
		this.save(message);

	}

	public boolean checkSpam(final String s) {
		final Configuration c = this.configurationService.findAll().iterator().next();
		boolean isSpam = false;
		if (s == null || StringUtils.isWhitespace(s))
			return isSpam;
		else {
			for (final String e : c.getSpamWords())
				if (s.contains(e))
					isSpam = true;
			return isSpam;
		}
	}

	//Sends a message to every actors in the system.

	public void broadcastMessage(final Message m) {
		Assert.notNull(m);
		final Collection<Actor> actors = this.actorService.findAll();
		final Actor principal = this.actorService.findByPrincipal();
		actors.remove(principal);

		for (final Actor a : actors)
			this.sendBroadcast(m, a);
	}

	public void sendBroadcast(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		final String boxName = "Notification box";
		final Box b = this.boxService.getSystemBoxByName(a.getId(), boxName);
		final Box box = this.boxService.getSystemBoxByName(this.actorService.findByPrincipal().getId(), "Out box");

		m.setRecipient(a);
		m.getBoxes().add(b);
		final Message saved = this.save(m);

		//Saving Notification box recipient box with the new message
		final Collection<Message> messagesOfRecipientInbox = b.getMessages();
		messagesOfRecipientInbox.add(saved);
		b.setMessages(messagesOfRecipientInbox);
		this.boxService.saveFromMessage(b);

		//Saving Outbox sender box with the new message
		final Collection<Message> messagesOfSenderOutbox = box.getMessages();
		messagesOfSenderOutbox.add(saved);
		box.setMessages(messagesOfSenderOutbox);
		this.boxService.saveFromMessage(box);

	}

	//Sends a message to the member associated to an request.

	public void requestStatusNotification(final Request r) {
		Assert.notNull(r);

		final Member m = r.getMember();

		final Message msg = this.create();
		msg.setSubject("Request status changed / El estado de la solicitud ha cambiado");
		msg.setBody("Your request status has been changed / El estado de tu solicitud ha sido cambiado.");
		msg.setPriority("HIGH");
		msg.setTags("Request status / Estado de la solicitud");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		this.send(msg, m);

	}

	//Sends a message to the member associated to an request.
	public void newEnrolmentNotification(final Enrolment e) {
		Assert.notNull(e);

		final Message msg = this.create();
		msg.setSubject("An enrolment has been registered / Se ha registrado una inscripcion");
		msg.setBody("An enrolment has been registered / Se ha registrado una inscripcion.");
		msg.setPriority("HIGH");
		msg.setTags("Enrolment created / Inscripcion registrada");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		this.send(msg, e.getMember());
	}

	//Sends a message to the brotherhoods members when a parade is published.
	public void paradePublished(final Parade p) {
		Assert.notNull(p);

		final Brotherhood b = p.getBrotherhood();
		final Collection<Member> activeMembers = this.memberService.activeMembersOfBrotherhood(b.getId());

		final Message msg = this.create();
		msg.setSubject("A parade has been published / Se ha publicado un desfile.");
		msg.setBody("A parade has been published / Se ha publicado un desfile.");
		msg.setPriority("HIGH");
		msg.setTags("Parade published / Desfile publicado");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		if (!activeMembers.isEmpty())
			for (final Member m : activeMembers)
				this.send(msg, m);
	}

	public void dropOutEnrolmentNotification(final Enrolment e) {
		Assert.notNull(e);

		final Message msg = this.create();
		msg.setSubject("A member has dropped out / Un miembro ha abandonado la inscripción.");
		msg.setBody("A member has dropped out / Un miembro ha abandonado la inscripción.");
		msg.setPriority("HIGH");
		msg.setTags("Member dropped out / Miembro abandonado");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		this.send(msg, e.getBrotherhood());
	}

	//Reconstruct
	public Message reconstruct(final Message m, final BindingResult binding) {
		Message result;
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.messageRepository.findOne(m.getId());

		result.setSubject(m.getSubject());
		result.setBody(m.getBody());
		result.setTags(m.getTags());
		result.setPriority(m.getPriority());
		result.setRecipient(m.getRecipient());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());

		return result;

	}

	//Reconstruct
	public Message reconstructBroadcast(final Message m, final BindingResult binding) {
		Message result;
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.messageRepository.findOne(m.getId());

		result.setSubject(m.getSubject());
		result.setBody(m.getBody());
		result.setTags(m.getTags());
		result.setPriority(m.getPriority());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());

		return result;

	}

	//Other methods
	public Collection<Message> getMessagesFromBox(final int id) {
		return this.messageRepository.messagesFromBox(id);

	}

	//Listing of the messages sent by a certain actor.
	public Collection<Message> sentMessagesForActor(final int id) {
		return this.messageRepository.sentMessagesForActor(id);
	}

	public void flush() {
		this.messageRepository.flush();
	}
}
