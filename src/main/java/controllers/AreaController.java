
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import domain.Area;

@Controller
@RequestMapping("area")
public class AreaController extends AbstractController {

	//Services

	@Autowired
	private AreaService	areaService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;

		final Area area = this.areaService.findOne(varId);

		result = new ModelAndView("area/display");
		result.addObject("area", area);
		result.addObject("requestURI", "area/display.do");

		return result;
	}

}
