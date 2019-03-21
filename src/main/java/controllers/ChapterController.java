
package controllers;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AreaService;
import services.ChapterService;
import services.ConfigurationService;
import domain.Area;
import domain.Chapter;
import domain.Configuration;
import forms.FormObjectChapter;

@Controller
@RequestMapping("chapter")
public class ChapterController extends AbstractController {

	//Services

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private ConfigurationService	configurationService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		FormObjectChapter foc;
		final Configuration config = this.configurationService.findAll().iterator().next();

		foc = new FormObjectChapter();
		foc.setPhone(config.getCountryCode());

		result = this.createEditModelAndView(foc);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Chapter chapter;

		chapter = (Chapter) this.actorService.findByPrincipal();
		Assert.notNull(chapter);
		result = this.editModelAndView(chapter);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid final FormObjectChapter foc, final BindingResult binding) {
		ModelAndView result;
		Chapter chapter;

		try {
			chapter = this.chapterService.reconstruct(foc, binding);
		} catch (final ValidationException oops) {
			return result = this.createEditModelAndView(foc);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(foc, "chapter.commit.error");
		}
		try {
			this.chapterService.save(chapter);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(foc, "chapter.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Chapter chapter, final BindingResult binding) {
		ModelAndView result;

		try {
			chapter = this.chapterService.reconstructPruned(chapter, binding);
		} catch (final ValidationException oops) {
			return result = this.editModelAndView(chapter);
		} catch (final Throwable oops) {
			return result = this.editModelAndView(chapter, "chapter.commit.error");
		}
		try {
			this.chapterService.save(chapter);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.editModelAndView(chapter, "chapter.commit.error");
		}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Chapter> chapters;

		chapters = this.chapterService.findAll();

		result = new ModelAndView("chapter/list");
		result.addObject("chapters", chapters);
		result.addObject("requestURI", "chapter/list.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;

		final Chapter chapter = this.chapterService.findOne(varId);

		result = new ModelAndView("chapter/display");
		result.addObject("chapter", chapter);
		result.addObject("requestURI", "chapter/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectChapter foc) {
		ModelAndView result;

		result = this.createEditModelAndView(foc, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectChapter foc, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("chapter/create");
		result.addObject("foc", foc);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "chapter/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Chapter chapter) {
		ModelAndView result;

		result = this.editModelAndView(chapter, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Chapter chapter, final String messageCode) {
		ModelAndView result;

		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("chapter/edit");
		result.addObject("chapter", chapter);
		result.addObject("areas", areas);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "chapter/edit.do");

		return result;
	}
}
