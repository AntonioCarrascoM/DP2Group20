
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository --------------------------------

	@Autowired
	private MessageRepository	messageRepository;

	//Supporting services ----------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BoxService			boxService;


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

		//Sets message as spam.
		//TODO CheckSpam
		//		if (this.actorService.checkSpam(message.getSubject()) || this.actorService.checkSpam(message.getBody()))
		//
		//			message.setSpam(true);

		if (message.getId() == 0)
			message.setSent(new Date(System.currentTimeMillis() - 1));

		final Message saved = this.messageRepository.save(message);

		//		this.actorService.checkSpam(saved.getBody());
		//		this.actorService.checkSpam(saved.getSubject());
		//		this.actorService.checkSpam(saved.getTags());

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

			final Collection<Box> messageBoxes = message.getBoxes();
			for (final Box b : messageBoxes) {
				final Collection<Message> messages = b.getMessages();
				messages.remove(message);
				b.setMessages(messages);
				this.boxService.saveFromMessage(b);
			}
			this.messageRepository.delete(message);
		}
	}

	//Other business methods ----------------------------

	public void send(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		//Parsing the message's subject and body for spam words.
		//TODO CheckSpam
		//		final boolean isSpamSubject = this.actorService.checkSpam(m.getSubject());
		//		final boolean isSpamBody = this.actorService.checkSpam(m.getBody());
		//		String boxName = "In box";
		//		if (isSpamSubject == true || isSpamBody == true)
		//			boxName = "Spam box";

		//Finds the system folder where the message must be sent to.
		final String boxName = "";
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

	//Sends a message to every actors in the system.

	public void broadcastMessage(final Message m) {
		Assert.notNull(m);
		final Collection<Actor> actors = this.actorService.findAll();
		final Actor principal = this.actorService.findByPrincipal();
		actors.remove(principal);

		for (final Actor a : actors)
			this.send(m, a);
	}
	//Sends a message to the customer and handyworker associated to an application.

	//	public void requestStatusNotification(final Request r) {
	//		Assert.notNull(r);
	//
	//		final HandyWorker hw = a.getHandyWorker();
	//		final Customer c = a.getFixUpTask().getCustomer();
	//
	//		final Message msg = this.create();
	//		msg.setSubject("Application status changed / El estado de la aplicación ha cambiado");
	//		msg.setBody("Your application status has been changed / El estado de tu aplicación ha sido cambiado.");
	//		msg.setPriority(Priority.HIGH);
	//		msg.setTags("Application status / Estado de la aplicación");
	//		msg.setSent(new Date(System.currentTimeMillis() - 1));
	//
	//		this.send(msg, c);
	//		this.send(msg, hw);
	//
	//	}

	//Other methods
	public Collection<Message> getMessagesFromBox(final int id) {
		return this.messageRepository.messagesFromBox(id);

	}
}
