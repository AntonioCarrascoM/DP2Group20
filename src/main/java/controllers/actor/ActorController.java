
package controllers.actor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AreaService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import domain.Sponsor;

@Controller
@RequestMapping("actor")
public class ActorController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private AreaService		areaService;


	//Deactivate

	@RequestMapping(value = "/deactivate", method = RequestMethod.GET)
	public ModelAndView deactivate() {
		Actor actor;

		actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		actor.getUserAccount().setInactive(true);
		this.actorService.save(actor);

		SecurityContextHolder.clearContext();

		return new ModelAndView("redirect:/welcome/index.do");
	}

	//Export data

	@RequestMapping(value = "/exportData", method = RequestMethod.GET)
	public ModelAndView exportData() {
		final Actor principal = this.actorService.findByPrincipal();

		final Brotherhood brotherhood = new Brotherhood();
		final Authority authBrotherhood = new Authority();
		authBrotherhood.setAuthority(Authority.BROTHERHOOD);

		final Chapter chapter = new Chapter();
		final Authority authChapter = new Authority();
		authChapter.setAuthority(Authority.CHAPTER);

		final Sponsor sponsor = new Sponsor();
		final Authority authSponsor = new Authority();
		authSponsor.setAuthority(Authority.SPONSOR);

		final Administrator admin = new Administrator();
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		final Member member = new Member();
		final Authority authMember = new Authority();
		authMember.setAuthority(Authority.MEMBER);

		final ObjectMapper mapper = new ObjectMapper();

		if (principal != null)
			try {
				if (principal.getUserAccount().getAuthorities().contains(authBrotherhood)) {
					final Brotherhood brotherhoodPrincipal = (Brotherhood) this.actorService.findByPrincipal();
					brotherhood.setName(brotherhoodPrincipal.getName());
					brotherhood.setMiddleName(brotherhoodPrincipal.getMiddleName());
					brotherhood.setSurname(brotherhoodPrincipal.getSurname());
					brotherhood.setPhoto(brotherhoodPrincipal.getPhoto());
					brotherhood.setEmail(brotherhoodPrincipal.getEmail());
					brotherhood.setPhone(brotherhoodPrincipal.getPhone());
					brotherhood.setAddress(brotherhoodPrincipal.getAddress());
					brotherhood.setTitle(brotherhoodPrincipal.getTitle());
					brotherhood.setEstablishmentDate(brotherhoodPrincipal.getEstablishmentDate());
					brotherhood.setPictures(brotherhoodPrincipal.getPictures());
					mapper.writeValue(new File("C:\\Temp\\MyPersonalData.txt"), brotherhood);
					return new ModelAndView("redirect:/welcome/index.do");
				} else if (principal.getUserAccount().getAuthorities().contains(authChapter)) {
					final Chapter chapterPrincipal = (Chapter) this.actorService.findByPrincipal();
					chapter.setName(chapterPrincipal.getName());
					chapter.setMiddleName(chapterPrincipal.getMiddleName());
					chapter.setSurname(chapterPrincipal.getSurname());
					chapter.setPhoto(chapterPrincipal.getPhoto());
					chapter.setEmail(chapterPrincipal.getEmail());
					chapter.setPhone(chapterPrincipal.getPhone());
					chapter.setAddress(chapterPrincipal.getAddress());
					chapter.setTitle(chapterPrincipal.getTitle());
					mapper.writeValue(new File("C:\\Temp\\MyPersonalData.txt"), chapter);
					return new ModelAndView("redirect:/welcome/index.do");
				} else if (principal.getUserAccount().getAuthorities().contains(authSponsor)) {
					final Sponsor sponsorPrincipal = (Sponsor) this.actorService.findByPrincipal();
					sponsor.setName(sponsorPrincipal.getName());
					sponsor.setMiddleName(sponsorPrincipal.getMiddleName());
					sponsor.setSurname(sponsorPrincipal.getSurname());
					sponsor.setPhoto(sponsorPrincipal.getPhoto());
					sponsor.setEmail(sponsorPrincipal.getEmail());
					sponsor.setPhone(sponsorPrincipal.getPhone());
					sponsor.setAddress(sponsorPrincipal.getAddress());
					mapper.writeValue(new File("C:\\Temp\\MyPersonalData.txt"), sponsor);
					return new ModelAndView("redirect:/welcome/index.do");
				} else if (principal.getUserAccount().getAuthorities().contains(authAdmin)) {
					final Administrator adminPrincipal = (Administrator) this.actorService.findByPrincipal();
					admin.setName(adminPrincipal.getName());
					admin.setMiddleName(adminPrincipal.getMiddleName());
					admin.setSurname(adminPrincipal.getSurname());
					admin.setPhoto(adminPrincipal.getPhoto());
					admin.setEmail(adminPrincipal.getEmail());
					admin.setPhone(adminPrincipal.getPhone());
					admin.setAddress(adminPrincipal.getAddress());
					mapper.writeValue(new File("C:\\Temp\\MyPersonalData.txt"), admin);
					return new ModelAndView("redirect:/welcome/index.do");
				} else if (principal.getUserAccount().getAuthorities().contains(authMember)) {
					final Member memberPrincipal = (Member) this.actorService.findByPrincipal();
					member.setName(memberPrincipal.getName());
					member.setMiddleName(memberPrincipal.getMiddleName());
					member.setSurname(memberPrincipal.getSurname());
					member.setPhoto(memberPrincipal.getPhoto());
					member.setEmail(memberPrincipal.getEmail());
					member.setPhone(memberPrincipal.getPhone());
					member.setAddress(memberPrincipal.getAddress());
					mapper.writeValue(new File("C:\\Temp\\MyPersonalData.txt"), member);
					return new ModelAndView("redirect:/welcome/index.do");
				}
			} catch (final JsonGenerationException e) {
				e.printStackTrace();
			} catch (final JsonMappingException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		else
			return new ModelAndView("redirect:/welcome/index.do");
		return new ModelAndView("redirect:/welcome/index.do");

	}
	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		final ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		result = this.createEditModelAndView(actor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.actorService.save(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "actor.commit.error");
			}
		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;

		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("areas", areas);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "actor/edit.do");

		return result;

	}
}
