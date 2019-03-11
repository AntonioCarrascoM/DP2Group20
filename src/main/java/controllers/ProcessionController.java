
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ProcessionService;
import domain.Procession;

@Controller
@RequestMapping("procession")
public class ProcessionController extends AbstractController {

	//Services

	@Autowired
	private ProcessionService	processionService;


	//	@Autowired
	//	private CategoryService		categoryService;

	//Listing

	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Procession> processions;

		processions = this.processionService.finalProcessionsForBrotherhood(varId);

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/listByBrotherhood.do");

		return result;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Procession> processions;

		processions = this.processionService.getFinalProcessions();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Procession procession;

		procession = this.processionService.findOne(varId);

		result = new ModelAndView("procession/display");
		result.addObject("procession", procession);
		result.addObject("requestURI", "procession/display.do");

		return result;
	}

}
