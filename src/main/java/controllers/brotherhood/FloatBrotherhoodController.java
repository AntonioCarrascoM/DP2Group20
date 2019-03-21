
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
import services.FloatService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Float;
import domain.Parade;

@Controller
@RequestMapping("float/brotherhood")
public class FloatBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private FloatService	floatService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ParadeService	paradeService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Float> floats;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		floats = brotherhood.getFloats();

		result = new ModelAndView("float/list");
		result.addObject("floats", floats);
		result.addObject("requestURI", "float/brotherhood/list.do");

		return result;
	}

	@RequestMapping(value = "/listByParade", method = RequestMethod.GET)
	public ModelAndView listByParade(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Float> floats;

		final Parade parade = this.paradeService.findOne(varId);
		floats = parade.getFloats();

		result = new ModelAndView("float/list");
		result.addObject("floats", floats);
		result.addObject("requestURI", "float/listByParade.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Float f;

		f = this.floatService.create();
		result = this.createEditModelAndView(f);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Float f;

		f = this.floatService.findOne(varId);

		if (f.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		Assert.notNull(f);
		result = this.createEditModelAndView(f);

		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Float f, final BindingResult binding) {
		ModelAndView result;

		try {
			f = this.floatService.reconstruct(f, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(f);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(f, "float.commit.error");
		}
		try {
			this.floatService.save(f);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(f, "float.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Float f, final BindingResult binding) {
		ModelAndView result;

		f = this.floatService.findOne(f.getId());

		if (f.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(f, "float.delete.error");
		else
			try {
				this.floatService.delete(f);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(f, "float.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Float> floats;
		Brotherhood brotherhood;
		Float f;

		result = new ModelAndView("float/list");
		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		floats = brotherhood.getFloats();

		f = this.floatService.findOne(varId);

		if (f.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.floatService.delete(f);
			result.addObject("floats", floats);
			result.addObject("requestURI", "float/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
		}

		return result;

	}
	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Float f) {
		ModelAndView result;

		result = this.createEditModelAndView(f, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Float f, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("float/edit");
		result.addObject("f", f);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "float/brotherhood/edit.do");

		return result;

	}

}
