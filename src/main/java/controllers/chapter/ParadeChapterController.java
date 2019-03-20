
package controllers.chapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ParadeService;
import controllers.AbstractController;
import domain.Parade;

@Controller
@RequestMapping("parade/chapter")
public class ParadeChapterController extends AbstractController {

	//Services

	@Autowired
	private ParadeService	paradeService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Parade> parades;

		parades = this.paradeService.findAll();

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
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
