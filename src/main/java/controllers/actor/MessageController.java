
package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BoxService;
import services.ConfigurationService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;
import domain.Configuration;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	//Services

	@Autowired
	private MessageService			messageService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	//Ancillary attributes

	private Message					currentMsg;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Message> messages;
		final Box b = this.boxService.findOne(varId);

		//Assertion the actor listing these messages has the correct privilege
		if (b.getActor().getId() != this.actorService.findByPrincipal().getId()) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		}
		messages = this.messageService.getMessagesFromBox(varId);

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");

		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Message message = this.messageService.findOne(varId);

		result = new ModelAndView("message/display");
		result.addObject("msg", message);
		result.addObject("requestURI", "message/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final ModelAndView result;
		Message message;

		message = this.messageService.create();
		result = this.createCreateModelAndView(message);

		return result;
	}

	//Sending

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Message message, final BindingResult binding) {
		ModelAndView result;

		message = this.messageService.reconstruct(message, binding);

		final Box box = this.boxService.getSystemBoxByName(message.getSender().getId(), "Out box");

		if (binding.hasErrors())
			result = this.createCreateModelAndView(message);
		else
			try {
				final Collection<Box> boxList = message.getBoxes();
				boxList.add(box);
				message.setBoxes(boxList);

				final Message saved = this.messageService.save(message);
				this.messageService.send(saved, saved.getRecipient());
				result = new ModelAndView("redirect:/box/list.do");

			} catch (final Throwable oops) {

				result = this.createCreateModelAndView(message, "message.commit.error");
			}

		return result;
	}

	//Moving

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Message message = this.messageService.findOne(varId);
		this.setCurrentMsg(message);

		result = this.createEditModelAndView(message);

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int varId) {
		ModelAndView result;
		final Box b = this.boxService.findOne(varId);

		try {
			final Message currentMsg = this.getCurrentMsg();
			this.messageService.move(currentMsg, b);
			result = new ModelAndView("redirect:/box/list.do");
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/box/list.do");
		}

		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final Message message = this.messageService.findOne(varId);

		try {
			this.messageService.delete(message);
			result = new ModelAndView("redirect:/box/list.do");

		} catch (final Throwable oops) {
			result = this.createCreateModelAndView(message, "message.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createCreateModelAndView(final Message message) {
		ModelAndView result;

		result = this.createCreateModelAndView(message, null);

		return result;
	}

	protected ModelAndView createCreateModelAndView(final Message message, final String messageCode) {
		final ModelAndView result;
		Configuration config;
		Collection<String> priorities;

		final Collection<Actor> recipients = this.actorService.findAll();
		recipients.remove(this.actorService.findByPrincipal());
		config = this.configurationService.findAll().iterator().next();
		priorities = config.getPriorityList();

		//No sé porqué no se muestran los mensajes del binding
		//final Collection<Box> boxes = message.getBoxes();

		result = new ModelAndView("message/create");
		result.addObject("recipients", recipients);
		result.addObject("priorities", priorities);
		//result.addObject("boxes", boxes);
		result.addObject("msg", message);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/create.do");

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Message message, final String messageCode) {
		ModelAndView result;
		Configuration config;
		Collection<String> priorities;

		final Collection<Box> boxes = this.actorService.findByPrincipal().getBoxes();
		config = this.configurationService.findAll().iterator().next();
		priorities = config.getPriorityList();

		result = new ModelAndView("message/edit");
		result.addObject("msg", message);
		result.addObject("boxes", boxes);
		result.addObject("priorities", priorities);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "message/edit.do");
		return result;

	}

	public Message getCurrentMsg() {
		return this.currentMsg;
	}

	public void setCurrentMsg(final Message currentMsg) {
		this.currentMsg = currentMsg;
	}
}
