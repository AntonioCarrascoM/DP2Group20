
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.FloatService;
import domain.Brotherhood;
import domain.Float;

@Controller
@RequestMapping("float")
public class FloatController extends AbstractController {

	//Services

	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Float> floats;

		floats = this.floatService.findAll();

		result = new ModelAndView("float/list");
		result.addObject("floats", floats);
		result.addObject("requestURI", "float/list.do");

		return result;
	}

	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Float> floats;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(varId);
		floats = brotherhood.getFloats();

		result = new ModelAndView("float/list");
		result.addObject("floats", floats);
		result.addObject("requestURI", "float/listByBrotherhood.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		Float floatX;

		floatX = this.floatService.findOne(varId);

		result = new ModelAndView("float/display");
		result.addObject("f", floatX);
		result.addObject("requestURI", "float/display.do");

		return result;
	}
}
