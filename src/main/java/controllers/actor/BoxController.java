
package controllers.actor;

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

import services.ActorService;
import services.BoxService;
import controllers.AbstractController;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box")
public class BoxController extends AbstractController {

	//Services

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Box> boxes;

		boxes = this.actorService.findByPrincipal().getBoxes();

		result = new ModelAndView("box/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	@RequestMapping(value = "/childrenList", method = RequestMethod.GET)
	public ModelAndView childrenList(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Box> boxes;
		final Box box;

		box = this.boxService.findOne(varId);
		boxes = box.getChildren();

		result = new ModelAndView("box/childrenList");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/childrenList.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Box box;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		box = this.boxService.create(actor);
		result = this.createEditModelAndView(box);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Box box;

		box = this.boxService.findOne(varId);
		Assert.notNull(box);
		result = this.createEditModelAndView(box);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Box box, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(box, "box.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Box box, final BindingResult binding) {
		ModelAndView result;

		try {
			this.boxService.delete(box);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(box, "box.commit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<Box> boxes;
		final Box box;
		result = new ModelAndView("box/list");
		boxes = this.actorService.findByPrincipal().getBoxes();

		box = this.boxService.findOne(varId);

		if (!box.getMessages().isEmpty())
			result.addObject("message", "box.delete.error");
		else if (!box.getChildren().isEmpty()) {
			for (final Box children : box.getChildren())
				if (!children.getMessages().isEmpty())
					result.addObject("message", "box.delete.error");
				else {
					this.boxService.delete(box);
					boxes = this.actorService.findByPrincipal().getBoxes();
				}

		} else {
			this.boxService.delete(box);
			boxes = this.actorService.findByPrincipal().getBoxes();
		}

		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/list.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Box box, final String messageCode) {
		ModelAndView result;
		final Collection<Box> boxes = this.actorService.findByPrincipal().getBoxes();

		result = new ModelAndView("box/edit");
		result.addObject("box", box);
		result.addObject("boxes", boxes);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "box/edit.do");

		return result;

	}
}
