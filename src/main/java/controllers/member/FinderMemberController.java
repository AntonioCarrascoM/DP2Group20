
package controllers.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.ConfigurationService;
import services.FinderService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Area;
import domain.Finder;
import domain.Parade;

@Controller
@RequestMapping("finder/member")
public class FinderMemberController extends AbstractController {

	//Services

	@Autowired
	private FinderService			finderService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private ConfigurationService	configurationService;


	//Listing the results of a finder

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		final Finder finder = this.finderService.findPrincipalFinder();
		Collection<Parade> parades = finder.getParades();
		final Long millis = this.configurationService.findAll().iterator().next().getExpireFinderMinutes() * 60000L;
		if (finder.getMoment() == null || (new Date(System.currentTimeMillis()).getTime() - finder.getMoment().getTime()) > millis)
			parades = this.finderService.limitResults(this.paradeService.findAll());

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
		result.addObject("requestURI", "finder/member/list.do");

		return result;
	}

	//Edition of parameters

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Finder finder;

		finder = this.finderService.findPrincipalFinder();
		Assert.notNull(finder);
		Collection<Area> areas = new ArrayList<>();
		areas = this.areaService.findAll();

		result = this.createEditModelAndView(finder);
		result.addObject("areas", areas);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;
		Collection<Parade> parades = new ArrayList<Parade>();

		if (binding.hasErrors())
			result = this.createEditModelAndView(finder);
		else
			try {

				parades = this.finderService.find(finder);
				finder.setParades(parades);

				this.finderService.save(finder);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(finder, "finder.commit.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Finder finder) {
		ModelAndView result;

		result = this.createEditModelAndView(finder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("finder/edit");
		result.addObject("finder", finder);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "finder/member/edit.do");

		return result;

	}
}
