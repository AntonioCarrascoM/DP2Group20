
package controllers.sponsor;

import java.util.Calendar;
import java.util.Collection;

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
import services.ParadeService;
import services.SponsorService;
import services.SponsorshipService;
import domain.Parade;
import domain.Sponsor;
import domain.Sponsorship;
import forms.FormObjectSponsorship;

@Controller
@RequestMapping("sponsorship/sponsor")
public class SponsorshipSponsorController {

	//Services

	@Autowired
	private SponsorshipService		sponsorshipService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		final FormObjectSponsorship foss;

		foss = new FormObjectSponsorship();
		result = this.createEditModelAndView(foss);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sponsorshipId) {
		ModelAndView result;
		Sponsorship sponsorship;

		sponsorship = this.sponsorshipService.findOne(sponsorshipId);
		Assert.notNull(sponsorship);
		result = this.editModelAndView(sponsorship);

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Sponsorship> sponsorships;
		final Sponsor sponsor = this.sponsorService.findOne(this.actorService.findByPrincipal().getId());

		//QUERY
		sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

	//Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			this.sponsorshipService.delete(sponsorship);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(sponsorship, "sponsorship.commit.error");
		}
		return result;
	}

	//Save

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Sponsorship sponsorship, final BindingResult binding) {
		ModelAndView result;

		try {
			sponsorship = this.sponsorshipService.reconstructPruned(sponsorship, binding);
		} catch (final Throwable oops) {
			return result = this.editModelAndView(sponsorship, "sponsorship.commit.error");
		}

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);

		if (binding.hasErrors())
			result = this.editModelAndView(sponsorship);
		else if (sponsorship.getCreditCard().getExpYear() < year)
			result = this.editModelAndView(sponsorship, "sponsorship.creditCard.error");
		else if (sponsorship.getCreditCard().getExpYear() == year && sponsorship.getCreditCard().getExpMonth() < month + 1)
			result = this.editModelAndView(sponsorship, "sponsorship.creditCard.error");
		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(sponsorship, "sponsorship.commit.error");
			}
		return result;
	}

	//Create POST

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(final FormObjectSponsorship foss, final BindingResult binding) {
		ModelAndView result;
		Sponsorship sponsorship;

		try {
			sponsorship = this.sponsorshipService.reconstruct(foss, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(foss, "sponsorship.reconstruct.error");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(foss);

		else
			try {
				this.sponsorshipService.save(sponsorship);
				result = new ModelAndView("redirect:/sponsorship/sponsor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(foss, "sponsorship.commit.error");
			}
		return result;
	}
	//Remove
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsorship sponsorship;

		result = new ModelAndView("sponsorship/list");

		final Sponsor sponsor = (Sponsor) this.actorService.findByPrincipal();
		sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

		sponsorship = this.sponsorshipService.findOne(varId);
		try {
			this.sponsorshipService.remove(sponsorship);
			sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = this.editModelAndView(sponsorship, "sponsorship.remove.error");
		}

		return result;
	}

	//Activate
	@RequestMapping(value = "/activate", method = RequestMethod.GET)
	public ModelAndView activate(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsorship sponsorship;

		result = new ModelAndView("sponsorship/list");

		final Sponsor sponsor = (Sponsor) this.actorService.findByPrincipal();
		sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

		sponsorship = this.sponsorshipService.findOne(varId);
		try {
			this.sponsorshipService.activate(sponsorship);
			sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = this.editModelAndView(sponsorship, "sponsorship.activate.error");
		}

		return result;
	}

	//Pay
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView pay(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;
		Sponsorship sponsorship;

		result = new ModelAndView("sponsorship/list");

		final Sponsor sponsor = (Sponsor) this.actorService.findByPrincipal();
		sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

		sponsorship = this.sponsorshipService.findOne(varId);
		try {
			this.sponsorshipService.pay(sponsorship);
			sponsorships = this.sponsorshipService.sponsorshipsFromSponsor(sponsor.getId());

			result.addObject("sponsorships", sponsorships);
			result.addObject("requestURI", "sponsorship/sponsor/list.do");

		} catch (final Throwable oops) {
			result = this.editModelAndView(sponsorship, "sponsorship.pay.error");
		}

		return result;
	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectSponsorship foss) {
		ModelAndView result;

		result = this.createEditModelAndView(foss, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectSponsorship foss, final String messageCode) {
		ModelAndView result;

		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		final Collection<Parade> parades = this.paradeService.paradesAccepted();

		result = new ModelAndView("sponsorship/create");
		result.addObject("foss", foss);
		result.addObject("makes", makes);
		result.addObject("parades", parades);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sponsorship/sponsor/create.do");

		return result;

	}

	protected ModelAndView editModelAndView(final Sponsorship sponsorship) {
		ModelAndView result;

		result = this.editModelAndView(sponsorship, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Sponsorship sponsorship, final String messageCode) {
		ModelAndView result;

		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		final Collection<Parade> parades = this.paradeService.paradesAccepted();

		result = new ModelAndView("sponsorship/edit");
		result.addObject("sponsorship", sponsorship);
		result.addObject("makes", makes);
		result.addObject("parades", parades);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "sponsorship/sponsor/edit.do");

		return result;
	}
}
