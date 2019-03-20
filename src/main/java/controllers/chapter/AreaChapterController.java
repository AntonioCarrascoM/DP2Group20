
package controllers.chapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AreaService;
import services.ChapterService;
import controllers.AbstractController;
import domain.Area;
import domain.Chapter;

@Controller
@RequestMapping("area/chapter")
public class AreaChapterController extends AbstractController {

	//Services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private AreaService		areaService;


	//Listing

	@RequestMapping(value = "/listA", method = RequestMethod.GET)
	public ModelAndView listOrDisplay() {
		final ModelAndView result;
		Collection<Area> areas;

		final Chapter chapter = (Chapter) this.actorService.findByPrincipal();
		if (chapter.getArea() != null) {
			final Area area = chapter.getArea();

			result = new ModelAndView("area/display");
			result.addObject("area", area);
			result.addObject("requestURI", "area/display.do");

			return result;

		} else {
			areas = this.areaService.areasWithNoChapterAssigned();

			result = new ModelAndView("area/list");
			result.addObject("areas", areas);
			result.addObject("requestURI", "area/chapter/list.do");

			return result;
		}
	}

	@RequestMapping(value = "/selfAssign", method = RequestMethod.GET)
	public ModelAndView selfAssign(@RequestParam final int varId) {
		final ModelAndView result;

		final Area area = this.areaService.findOne(varId);

		if (this.chapterService.getChapterForArea(varId) != null && this.chapterService.getChapterForArea(varId).getArea() != null)
			return new ModelAndView("redirect:/welcome/index.do");
		else {
			final Chapter chapter = (Chapter) this.actorService.findByPrincipal();

			chapter.setArea(area);
			this.chapterService.save(chapter);

			result = new ModelAndView("chapter/display");
			result.addObject("chapter", chapter);
			result.addObject("requestURI", "chapter/display.do");

			return result;
		}
	}
}
