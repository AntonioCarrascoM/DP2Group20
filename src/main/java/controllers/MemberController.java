
package controllers;

import java.util.Collection;

import javax.validation.Valid;
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
import services.ConfigurationService;
import services.MemberService;
import domain.Configuration;
import domain.Member;
import forms.FormObjectMember;

@Controller
@RequestMapping("member")
public class MemberController extends AbstractController {

	//Services

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Member member;
		member = (Member) this.actorService.findByPrincipal();
		Assert.notNull(member);
		result = this.editModelAndView(member);

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		FormObjectMember fom;
		final Configuration config = this.configurationService.findAll().iterator().next();

		fom = new FormObjectMember();
		fom.setPhone(config.getCountryCode());

		result = this.createEditModelAndView(fom);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Member member, final BindingResult binding) {
		ModelAndView result;

		try {
			member = this.memberService.reconstructPruned(member, binding);
		} catch (final ValidationException oops) {
			return this.editModelAndView(member);
		} catch (final Throwable oops) {
			return this.editModelAndView(member, "member.commit.error");
		}
		try {
			this.memberService.save(member);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(member, "member.commit.error");
		}
		return result;
	}

	//Create POST

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid final FormObjectMember fom, final BindingResult binding) {
		ModelAndView result;
		Member member;

		try {
			member = this.memberService.reconstruct(fom, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fom);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fom, "member.reconstruct.error");
		}

		try {
			this.memberService.save(member);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fom, "member.commit.error");
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

	protected ModelAndView createEditModelAndView(final FormObjectMember fom) {
		ModelAndView result;

		result = this.createEditModelAndView(fom, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectMember fom, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("member/create");
		result.addObject("fom", fom);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "member/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Member member) {
		ModelAndView result;

		result = this.editModelAndView(member, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Member member, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("member/edit");
		result.addObject("member", member);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "member/edit.do");

		return result;
	}
}
