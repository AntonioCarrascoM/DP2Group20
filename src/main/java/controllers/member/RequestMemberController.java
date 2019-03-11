
package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Member;
import domain.Procession;
import domain.Request;
import domain.Status;

@Controller
@RequestMapping("request/member")
public class RequestMemberController extends AbstractController {

	//Services

	@Autowired
	private RequestService		requestService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ProcessionService	processionService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Request request;
		final Collection<Procession> processions = this.processionService.processionsForRequestByMember((this.actorService.findByPrincipal()).getId());

		request = this.requestService.create();
		result = this.createEditModelAndView(request);
		result.addObject("processions", processions);

		return result;
	}
	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Request request, final BindingResult binding) {
		ModelAndView result;
		Request saved;

		final Request req = this.requestService.findOne(request.getId());

		try {
			request = this.requestService.reconstruct(request, binding);
		} catch (final Throwable oops) {
			final Collection<Request> requests = req.getProcession().getRequests();
			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("message", "request.reconstruct.error");
			return result;
		}

		if (request.getId() != 0)
			result = this.createEditModelAndView(request);

		if (binding.hasErrors())
			result = this.createEditModelAndView(request);
		else
			try {
				saved = this.requestService.save(request);
				result = new ModelAndView("redirect:/request/member/display.do?varId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request, "request.commit.error");
			}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Request> requests;

		final Member m = (Member) this.actorService.findByPrincipal();
		requests = m.getRequests();

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/member/list.do");

		return result;
	}
	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Request request = this.requestService.findOne(varId);
		Assert.notNull(request);

		result = new ModelAndView("request/display");
		result.addObject("request", request);
		result.addObject("requestURI", "request/member/display.do");

		return result;
	}

	//Delete
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Request> requests;
		Request request;

		result = new ModelAndView("request/list");

		final Member m = (Member) this.actorService.findByPrincipal();
		requests = m.getRequests();

		request = this.requestService.findOne(varId);
		if (request.getStatus() != Status.PENDING)
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.requestService.delete(request);
			requests = m.getRequests();

			result.addObject("requests", requests);
			result.addObject("requestURI", "request/member/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.delete.error");
		}

		return result;
	}

	//Delete POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Request request, final BindingResult binding) {
		ModelAndView result;

		request = this.requestService.findOne(request.getId());

		if (request.getStatus().equals("PENDING"))
			result = this.createEditModelAndView(request, "request.status.delete.error");
		else
			try {
				this.requestService.delete(request);
				result = new ModelAndView("redirect:/request/member/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(request, "request.delete.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Request request) {
		ModelAndView result;

		result = this.createEditModelAndView(request, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Request request, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("request/edit");
		result.addObject("request", request);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "request/member/edit.do");

		return result;

	}
}
