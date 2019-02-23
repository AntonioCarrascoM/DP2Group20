
package controllers;

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
import services.MemberService;
import domain.Member;

@Controller
@RequestMapping("member")
public class MemberController extends AbstractController {

	//Services

	@Autowired
	private MemberService	memberService;

	@Autowired
	private ActorService	actorService;


	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Member member;
		member = (Member) this.actorService.findByPrincipal();
		Assert.notNull(member);
		result = this.createEditModelAndView(member);

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Member member;

		member = this.memberService.create();
		result = this.createEditModelAndView(member);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Member member, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(member);

		else
			try {
				this.memberService.save(member);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(member, "member.commit.error");
			}
		return result;
	}

	//Listing 

	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Member> members;

		members = this.memberService.activeMembersOfBrotherhood(varId);

		result = new ModelAndView("member/list");
		result.addObject("members", members);
		result.addObject("requestURI", "member/listByBrotherhood.do");

		return result;
	}

	//Display 

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;

		final Member member = this.memberService.findOne(varId);
		Assert.notNull(member);

		result = new ModelAndView("member/display");
		result.addObject("member", member);
		result.addObject("requestURI", "member/display.do");

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
		result.addObject("requestURI", "member/edit.do");

		return result;
	}
}
