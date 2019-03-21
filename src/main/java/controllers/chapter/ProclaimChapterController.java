
package controllers.chapter;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ProclaimService;
import controllers.AbstractController;
import domain.Chapter;
import domain.Proclaim;

@Controller
@RequestMapping("proclaim/chapter")
public class ProclaimChapterController extends AbstractController {

	//Services

	@Autowired
	private ProclaimService	proclaimService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Proclaim> proclaims;
		Chapter chapter;

		chapter = (Chapter) this.actorService.findByPrincipal();
		proclaims = this.proclaimService.getProclaimsForChapter(chapter.getId());

		result = new ModelAndView("proclaim/list");
		result.addObject("proclaims", proclaims);
		result.addObject("chapter", chapter);
		result.addObject("requestURI", "proclaim/chapter/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Proclaim proclaim;

		proclaim = this.proclaimService.create();
		result = this.createEditModelAndView(proclaim);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Proclaim proclaim, final BindingResult binding) {
		ModelAndView result;

		try {
			proclaim = this.proclaimService.reconstruct(proclaim, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(proclaim);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(proclaim, "proclaim.commit.error");
		}

		try {
			this.proclaimService.save(proclaim);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(proclaim, "proclaim.commit.error");
		}
		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Proclaim proclaim) {
		ModelAndView result;

		result = this.createEditModelAndView(proclaim, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Proclaim proclaim, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("proclaim/edit");
		result.addObject("proclaim", proclaim);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "proclaim/chapter/edit.do");

		return result;

	}

}
