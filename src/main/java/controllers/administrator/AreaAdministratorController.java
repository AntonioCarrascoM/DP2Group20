
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

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
@RequestMapping("area/administrator")
public class AreaAdministratorController extends AbstractController {

	//Services

	@Autowired
	private AreaService	areaService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Area> areas;

		areas = this.areaService.findAll();

		result = new ModelAndView("area/list");
		result.addObject("areas", areas);
		result.addObject("requestURI", "area/administrator/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Area area;

		area = this.areaService.create();
		result = this.createEditModelAndView(area);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Area area;

		area = this.areaService.findOne(varId);
		Assert.notNull(area);
		result = this.createEditModelAndView(area);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Area area, final BindingResult binding) {
		ModelAndView result;
		//for (final ObjectError oe : binding.getAllErrors())
		//System.out.println(oe);
		if (binding.hasErrors())
			result = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Area> areas;
		Area area;
		result = new ModelAndView("area/list");
		areas = this.areaService.findAll();
		area = this.areaService.findOne(varId);

		if (!area.getBrotherhoods().isEmpty())
			result = this.createEditModelAndView(area, "area.brotherhood.error");
		else
			try {
				this.areaService.delete(area);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.delete.error");
			}
		result.addObject("areas", areas);
		result.addObject("requestURI", "area/administrator/list.do");

		return result;
	}

	//Delete POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Area area, final BindingResult binding) {
		ModelAndView result;

		if (!area.getBrotherhoods().isEmpty())
			result = this.createEditModelAndView(area, "area.brotherhood.error");
		else
			try {
				this.areaService.delete(area);
				result = new ModelAndView("redirect:/area/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(area, "area.delete.error");
			}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Area area) {
		ModelAndView result;

		result = this.createEditModelAndView(area, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area, final String messageCode) {
		ModelAndView result;
		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("area/edit");
		result.addObject("area", area);
		result.addObject("areas", areas);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "area/administrator/edit.do");

		return result;

	}
}
