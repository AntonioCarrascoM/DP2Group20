
package controllers.member;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RequestService;
import controllers.AbstractController;
import domain.Member;
import domain.Request;

@Controller
@RequestMapping("request/member")
public class RequestMemberController extends AbstractController {

	//Services

	@Autowired
	private RequestService	requestService;

	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Request request;

		request = this.requestService.create(varId);
		result = this.createEditModelAndView(request);

		return result;
	}
	//Edition

	//A REQUEST CANNOT BE UPDATED

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int varId) {
	//		final ModelAndView result;
	//		final Request request = this.requestService.findOne(varId);
	//		Assert.notNull(request);
	//
	//		if (request.getMember().getId() != this.actorService.findByPrincipal().getId())
	//			return new ModelAndView("redirect:/welcome/index.do");
	//		result = this.createEditModelAndView(request);
	//
	//		return result;
	//	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Request request, final BindingResult binding) {
		ModelAndView result;
		Request saved;

		//SI LA REQUEST NO ES NUEVA NO DEBE DEJAR GUARDARLA
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
		Collection<Request> endorsements;

		final Member m = (Member) this.actorService.findByPrincipal();
		endorsements = m.getRequests();

		result = new ModelAndView("request/list");
		result.addObject("endorsements", endorsements);
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
	public ModelAndView delete(@RequestParam final int endorsementId) {
		ModelAndView result;
		Collection<Request> endorsements;
		Request request;

		result = new ModelAndView("request/list");

		final Member m = (Member) this.actorService.findByPrincipal();
		endorsements = m.getRequests();

		request = this.requestService.findOne(endorsementId);
		try {
			this.requestService.delete(request);
			endorsements = m.getRequests();

			result.addObject("endorsements", endorsements);
			result.addObject("requestURI", "request/member/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.delete.error");
		}

		return result;
	}

	//Delete POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Request request, final BindingResult binding) {
		ModelAndView result;
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
