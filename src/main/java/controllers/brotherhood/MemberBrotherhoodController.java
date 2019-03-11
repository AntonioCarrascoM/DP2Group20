
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private MemberService	memberService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/enrolableList", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Member> members;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		members = this.memberService.enrolableMembersOfBrotherhood(brotherhood.getId());

		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", "member/brotherhood/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Member member) {
		ModelAndView result;

		result = this.createEditModelAndView(member, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Member member, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("member/edit");
		result.addObject("member", member);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "member/brotherhood/edit.do");

		return result;

	}

}
