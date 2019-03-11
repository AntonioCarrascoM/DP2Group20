
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
import services.BrotherhoodService;
import services.EnrolmentService;
import services.PositionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Position;

@Controller
@RequestMapping("enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


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

		final Brotherhood bro = (Brotherhood) this.actorService.findByPrincipal();

		if (binding.hasErrors())
			result = this.createEditModelAndView(enrolment);

		else
			try {
				if (this.brotherhoodService.nonEnrolmentableMembers(bro.getId()).contains(enrolment.getMember()))
					result = this.createEditModelAndView(enrolment, "enrolment.member.error");
				else {
					this.enrolmentService.saveFromBrotherhood(enrolment);
					result = new ModelAndView("redirect:/enrolment/brotherhood/list.do");
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(enrolment, "enrolment.brotherhood.area.error");
			}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Enrolment> enrolments;
		Brotherhood b;

		b = (Brotherhood) this.actorService.findByPrincipal();

		enrolments = b.getEnrolments();

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("brotherhood", b);
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
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Enrolment> enrolments;
		Enrolment enrolment;

		result = new ModelAndView("enrolment/list");

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		enrolments = b.getEnrolments();

		enrolment = this.enrolmentService.findOne(varId);
		try {
			this.enrolmentService.delete(enrolment);
			enrolments = b.getEnrolments();

			result.addObject("enrolments", enrolments);
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

	//Drop
	@RequestMapping(value = "/drop", method = RequestMethod.GET)
	public ModelAndView drop(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Enrolment> enrolments;
		Enrolment enrolment;

		result = new ModelAndView("enrolment/list");

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		enrolments = b.getEnrolments();

		enrolment = this.enrolmentService.findOne(varId);
		try {
			this.enrolmentService.drop(enrolment);
			enrolments = b.getEnrolments();

			result.addObject("enrolments", enrolments);
			result.addObject("requestURI", "enrolment/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enrolment, "enrolment.drop.error");
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
		final Collection<Position> positions = this.positionService.findAll();

		result = new ModelAndView("enrolment/edit");
		result.addObject("positions", positions);
		result.addObject("enrolment", enrolment);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "enrolment/brotherhood/edit.do");

		return result;

	}
}
