
package controllers.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import domain.Member;
import domain.Position;

@Controller
@RequestMapping("enrolment/member")
public class EnrolmentMemberController extends AbstractController {

	//Services

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Enrolment> enrolments;

		final Member m = (Member) this.actorService.findByPrincipal();
		enrolments = m.getEnrolments();

		result = new ModelAndView("enrolment/list");
		result.addObject("enrolments", enrolments);
		result.addObject("requestURI", "enrolment/member/list.do");

		return result;
	}

	@RequestMapping(value = "/listMyBrotherhoods", method = RequestMethod.GET)
	public ModelAndView listMyBrotherhoods() {
		final ModelAndView result;
		final Collection<Brotherhood> brotherhoods;

		final Member m = (Member) this.actorService.findByPrincipal();
		brotherhoods = this.brotherhoodService.enrolmentMemberBrotherhoods(m.getId());

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/list.do");

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
		result.addObject("requestURI", "enrolment/member/display.do");

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Enrolment> enrolments;
		Enrolment enrolment;

		result = new ModelAndView("enrolment/list");

		final Member m = (Member) this.actorService.findByPrincipal();
		enrolments = m.getEnrolments();

		enrolment = this.enrolmentService.findOne(varId);
		try {
			this.enrolmentService.delete(enrolment);
			enrolments = m.getEnrolments();

			result.addObject("enrolments", enrolments);
			result.addObject("requestURI", "enrolment/member/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(enrolment, "enrolment.delete.error");
		}

		return result;
	}

	//Drop

	@RequestMapping(value = "/drop", method = RequestMethod.GET)
	public ModelAndView drop(@RequestParam final int varId) {
		ModelAndView result;
		final Enrolment enrolment = this.enrolmentService.findOne(varId);
		Assert.notNull(enrolment);

		if (enrolment.getMember().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {

			Assert.isTrue(enrolment.getDropOutMoment() == null);
			this.enrolmentService.drop(enrolment);

			result = new ModelAndView("redirect:/enrolment/member/list.do");

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/enrolment/member/list.do");
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
		result.addObject("requestURI", "enrolment/member/edit.do");

		return result;

	}
}
