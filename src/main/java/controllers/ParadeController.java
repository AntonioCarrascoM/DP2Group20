
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParadeService;
import services.SponsorshipService;
import domain.Parade;
import domain.Sponsorship;

@Controller
@RequestMapping("parade")
public class ParadeController extends AbstractController {

	//Services

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//	@Autowired
	//	private CategoryService		categoryService;

	//Listing

	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Parade> parades;

		parades = this.paradeService.finalAcceptedParadesForBrotherhood(varId);

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "parade/listByBrotherhood.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Parade> parades;

		parades = this.paradeService.getFinalAcceptedParades();

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "parade/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		final Parade parade;
		Sponsorship sponsorship;

		sponsorship = this.paradeService.selectRandomSponsorship(varId);

		if (sponsorship != null)
			this.sponsorshipService.saveFromParade(sponsorship);

		parade = this.paradeService.findOne(varId);

		result = new ModelAndView("parade/display");
		result.addObject("parade", parade);
		result.addObject("sponsorship", sponsorship);
		result.addObject("requestURI", "parade/display.do");

		return result;
	}

}
