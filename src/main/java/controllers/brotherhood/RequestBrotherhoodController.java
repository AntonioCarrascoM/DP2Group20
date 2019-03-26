
package controllers.brotherhood;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ParadeService;
import services.RequestService;
import domain.Parade;
import domain.Request;

@Controller
@RequestMapping("request/brotherhood")
public class RequestBrotherhoodController {

	//Supporting services

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private RequestService	requestService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Request> requests;

		final Parade parade = this.paradeService.findOne(varId);

		if (parade.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		requests = this.requestService.requestOrderByStatus(varId);

		result = new ModelAndView("request/list");
		result.addObject("requests", requests);
		result.addObject("requestURI", "request/brotherhood/list.do");

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
		result.addObject("requestURI", "request/brotherhood/display.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Request request = this.requestService.findOne(varId);
		Assert.notNull(request);

		if (request.getParade().getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(request);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Request request, final BindingResult binding) {
		ModelAndView result;
		Request saved;

		final Request req = this.requestService.findOne(request.getId());

		try {
			request = this.requestService.reconstruct(request, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(request);
		} catch (final NullPointerException oops) {
			final Collection<Request> requests = this.requestService.requestOrderByStatus(req.getParade().getId());
			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("message", "request.reason.error");
			return result;
		} catch (final RuntimeException oops) {
			final Collection<Request> requests = this.requestService.requestOrderByStatus(req.getParade().getId());
			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("message", "request.maxPosition.error");
			return result;
		} catch (final Throwable oops) {
			final Collection<Request> requests = this.requestService.requestOrderByStatus(req.getParade().getId());
			result = new ModelAndView("request/list");
			result.addObject("requests", requests);
			result.addObject("message", "request.reconstruct.error");
			return result;
		}

		try {
			saved = this.requestService.save(request);
			result = new ModelAndView("redirect:/request/brotherhood/list.do?varId=" + saved.getParade().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(request, "request.commit.error");
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

		final Request req = this.requestService.findOne(request.getId());

		final Integer maxRow = req.getParade().getMaxRow();
		final Integer maxColumn = req.getParade().getMaxColumn();

		result = new ModelAndView("request/edit");
		result.addObject("maxRow", maxRow);
		result.addObject("maxColumn", maxColumn);
		result.addObject("request", request);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "request/brotherhood/edit.do");

		return result;

	}

}
