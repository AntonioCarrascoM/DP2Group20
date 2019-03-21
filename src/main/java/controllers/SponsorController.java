
package controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SponsorService;
import domain.Sponsor;
import forms.FormObjectSponsor;

@Controller
@RequestMapping("sponsor")
public class SponsorController extends AbstractController {

	//Services

	@Autowired
	private SponsorService	sponsorService;

	@Autowired
	private ActorService	actorService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectSponsor fos;

		fos = new FormObjectSponsor();
		result = this.createEditModelAndView(fos);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Sponsor sponsor;
		sponsor = (Sponsor) this.actorService.findByPrincipal();
		Assert.notNull(sponsor);
		result = this.editModelAndView(sponsor);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;

		try {
			sponsor = this.sponsorService.reconstructPruned(sponsor, binding);
		} catch (final ValidationException oops) {
			return this.editModelAndView(sponsor);
		} catch (final Throwable oops) {
			return this.editModelAndView(sponsor, "sponsor.commit.error");
		}
		try {
			this.sponsorService.save(sponsor);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(sponsor, "sponsor.commit.error");
		}
		return result;
	}

	//Create POST

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectSponsor fos, final BindingResult binding) {
		ModelAndView result;
		Sponsor sponsor;

		try {
			sponsor = this.sponsorService.reconstruct(fos, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(fos);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(fos, "sponsor.reconstruct.error");
		}

		try {
			this.sponsorService.save(sponsor);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fos, "sponsor.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectSponsor fos) {
		ModelAndView result;

		result = this.createEditModelAndView(fos, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectSponsor fos, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsor/create");
		result.addObject("fos", fos);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sponsor/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Sponsor sponsor) {
		ModelAndView result;

		result = this.editModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Sponsor sponsor, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sponsor/edit.do");

		return result;
	}
}
