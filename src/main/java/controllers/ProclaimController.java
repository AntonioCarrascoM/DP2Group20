
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProclaimService;
import domain.Proclaim;

@Controller
@RequestMapping("proclaim")
public class ProclaimController extends AbstractController {

	//Services

	@Autowired
	private ProclaimService	proclaimService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Proclaim> proclaims;

		proclaims = this.proclaimService.findAll();

		result = new ModelAndView("proclaim/list");
		result.addObject("proclaims", proclaims);
		result.addObject("requestURI", "proclaim/list.do");

		return result;
	}

	@RequestMapping(value = "/listByChapter", method = RequestMethod.GET)
	public ModelAndView listByChapter(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Proclaim> proclaims;

		proclaims = this.proclaimService.getProclaimsForChapter(varId);

		result = new ModelAndView("proclaim/list");
		result.addObject("proclaims", proclaims);
		result.addObject("requestURI", "proclaim/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Proclaim proclaim;

		proclaim = this.proclaimService.findOne(varId);

		result = new ModelAndView("proclaim/display");
		result.addObject("proclaim", proclaim);
		result.addObject("requestURI", "proclaim/display.do");

		return result;
	}

}
