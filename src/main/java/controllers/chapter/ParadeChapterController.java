
package controllers.chapter;

import java.util.Collection;

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
import services.ChapterService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Chapter;
import domain.Parade;

@Controller
@RequestMapping("parade/chapter")
public class ParadeChapterController extends AbstractController {

	//Services

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChapterService	chapterService;


	//Edit GET
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Parade parade = this.paradeService.findOne(varId);

		final Chapter chap = this.chapterService.getChapterForArea(parade.getBrotherhood().getArea().getId());
		if (chap.getId() != this.actorService.findByPrincipal().getId() || parade.getFinalMode() == false)
			return new ModelAndView("redirect:/welcome/index.do");

		parade = this.paradeService.findOne(varId);

		Assert.notNull(parade);
		result = this.createEditModelAndView(parade);

		result.addObject("parade", parade);

		return result;
	}

	//Edit POST
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Parade parade, final BindingResult binding) {
		ModelAndView result;

		try {
			parade = this.paradeService.reconstruct(parade, binding);
		} catch (final ValidationException oops) {
			return result = this.createEditModelAndView(parade);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(parade, "parade.commit.error");
		}
		try {
			this.paradeService.saveFromChapter(parade);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}
		return result;
	}

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Parade> parades;
		Chapter chapter;

		chapter = (Chapter) this.actorService.findByPrincipal();
		result = new ModelAndView("parade/list");
		if (chapter.getArea() != null && !this.paradeService.finalParadesByArea(chapter.getArea().getId()).isEmpty()) {
			parades = this.paradeService.finalParadesByArea(chapter.getArea().getId());
			result.addObject("parades", parades);
		}

		result.addObject("chapter", chapter);
		result.addObject("requestURI", "parade/chapter/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Parade parade) {
		ModelAndView result;

		result = this.createEditModelAndView(parade, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("parade/edit");
		result.addObject("parade", parade);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "parade/chapter/edit.do");

		return result;

	}

}
