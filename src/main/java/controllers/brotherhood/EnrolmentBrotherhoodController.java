
package controllers.brotherhood;

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
import services.EnrolmentService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;

@Controller
@RequestMapping("enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private EnrolmentService	enrolmentService;

	private ActorService		actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Enrolment enrolment;

		enrolment = this.enrolmentService.create(varId);
		result = this.createEditModelAndView(enrolment);

		return result;
	}
	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		final Enrolment enrolment = this.enrolmentService.findOne(varId);
		Assert.notNull(enrolment);

		if (enrolment.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(enrolment);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;
		Enrolment saved;

		if (binding.hasErrors())
			result = this.createEditModelAndView(enrolment);
		else
			try {
				saved = this.enrolmentService.save(enrolment);
				result = new ModelAndView("redirect:/enrolment/brotherhood/display.do?varId=" + saved.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enrolment, "enrolment.commit.error");
			}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Enrolment> endorsements;

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		endorsements = b.getEnrolments();

		result = new ModelAndView("enrolment/list");
		result.addObject("endorsements", endorsements);
		result.addObject("requestURI", "enrolment/brotherhood/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Enrolment enrolment = this.enrolmentService.findOne(varId);
		Assert.notNull(enrolment);

		result = new ModelAndView("enrolment/display");
		result.addObject("enrolment", enrolment);
		result.addObject("requestURI", "enrolment/brotherhood/display.do");

		return result;
	}

	//Delete 
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int endorsementId) {
		ModelAndView result;
		Collection<Enrolment> endorsements;
		Enrolment enrolment;

		result = new ModelAndView("enrolment/list");

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		endorsements = b.getEnrolments();

		enrolment = this.enrolmentService.findOne(endorsementId);
		try {
			this.enrolmentService.delete(enrolment);
			endorsements = b.getEnrolments();

			result.addObject("endorsements", endorsements);
			result.addObject("requestURI", "enrolment/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enrolment, "enrolment.delete.error");
		}

		return result;
	}

	//Delete POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Enrolment enrolment, final BindingResult binding) {
		ModelAndView result;

		try {
			this.enrolmentService.delete(enrolment);
			result = new ModelAndView("redirect:/enrolment/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enrolment, "enrolment.delete.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Enrolment enrolment) {
		ModelAndView result;

		result = this.createEditModelAndView(enrolment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Enrolment enrolment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("enrolment/edit");
		result.addObject("enrolment", enrolment);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "enrolment/brotherhood/edit.do");

		return result;

	}
}
