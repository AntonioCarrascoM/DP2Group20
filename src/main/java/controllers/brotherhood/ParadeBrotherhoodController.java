
package controllers.brotherhood;

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
import services.ParadeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Parade;

@Controller
@RequestMapping("parade/brotherhood")
public class ParadeBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Parade> parades;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		parades = brotherhood.getParades();

		result = new ModelAndView("parade/list");
		result.addObject("parades", parades);
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", "parade/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Parade parade;

		parade = this.paradeService.create();
		result = this.createEditModelAndView(parade);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Parade parade = this.paradeService.findOne(varId);

		if (parade.getBrotherhood().getId() != this.actorService.findByPrincipal().getId() || parade.getFinalMode() == true)
			return new ModelAndView("redirect:/welcome/index.do");

		parade = this.paradeService.findOne(varId);

		Assert.notNull(parade);
		result = this.createEditModelAndView(parade);

		return result;
	}

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

			if (parade.getFinalMode() == true) {
				parade.setFinalMode(false);
				this.paradeService.save(parade, true);
			} else
				this.paradeService.save(parade, false);

			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Parade parade, final BindingResult binding) {
		ModelAndView result;

		parade = this.paradeService.findOne(parade.getId());

		if (parade.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(parade, "parade.delete.error");
		else
			try {
				this.paradeService.delete(parade);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(parade, "parade.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Parade> parades;
		Brotherhood brotherhood;
		Parade parade;

		result = new ModelAndView("parade/list");
		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		parades = brotherhood.getParades();

		parade = this.paradeService.findOne(varId);

		if (parade.getBrotherhood().getId() != this.actorService.findByPrincipal().getId() || parade.getFinalMode() == true) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		} else
			try {
				this.paradeService.delete(parade);
				result.addObject("parades", parades);
				result.addObject("requestURI", "parade/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:list.do");
			}

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
		result.addObject("requestURI", "parade/brotherhood/edit.do");

		return result;

	}

}
