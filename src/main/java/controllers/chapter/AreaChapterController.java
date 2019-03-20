
package controllers.chapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("area/chapter")
public class AreaChapterController extends AbstractController {

	//Services

	@Autowired
	private AreaService	areaService;


	//Listing

	@RequestMapping(value = "/listA", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Area> areas;
		
		//TODO query que coja las areas no asignadas
		areas = this.areaService.findAll();

		result = new ModelAndView("area/list");
		result.addObject("areas", areas);
		result.addObject("requestURI", "area/chapter/list.do");

		return result;
	}
}
