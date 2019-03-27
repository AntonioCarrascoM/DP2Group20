
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AreaService;
import services.BrotherhoodService;
import controllers.AbstractController;
import domain.Area;
import domain.Brotherhood;

@Controller
@RequestMapping("area/brotherhood")
public class AreaBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	ActorService		actorService;

	@Autowired
	BrotherhoodService	brotherhoodService;

	@Autowired
	AreaService			areaService;


	//List areas
	@RequestMapping(value = "/listA", method = RequestMethod.GET)
	public ModelAndView listOrDisplay() {
		final ModelAndView result;
		Collection<Area> areas;

		final Brotherhood brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		if (brotherhood.getArea() != null) {
			final Area area = brotherhood.getArea();

			result = new ModelAndView("area/display");
			result.addObject("area", area);
			result.addObject("requestURI", "area/display.do");

			return result;

		} else {
			areas = this.areaService.findAll();

			result = new ModelAndView("area/list");
			result.addObject("areas", areas);
			result.addObject("requestURI", "area/brotherhood/list.do");

			return result;
		}
	}

	//Self assign an area
	@RequestMapping(value = "/selfAssign", method = RequestMethod.GET)
	public ModelAndView selfAssign(@RequestParam final int varId) {

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();

		final Area area = this.areaService.findOne(varId);

		if (b.getArea() != null)
			return new ModelAndView("redirect:/welcome/index.do");
		else
			b.setArea(area);
		this.brotherhoodService.save(b);

		return new ModelAndView("redirect:/welcome/index.do");
	}
}
