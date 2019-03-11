
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

import services.PositionService;
import controllers.AbstractController;
import domain.Position;

@Controller
@RequestMapping("position/administrator")
public class PositionAdministratorController extends AbstractController {

	//Services

	@Autowired
	private PositionService	positionService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Position> positions;

		positions = this.positionService.findAll();

		result = new ModelAndView("position/list");
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/administrator/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Position position;

		position = this.positionService.create();
		result = this.createEditModelAndView(position);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Position position;

		position = this.positionService.findOne(varId);
		Assert.notNull(position);
		result = this.createEditModelAndView(position);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Position position, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(position);
		else
			try {
				this.positionService.save(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Position> positions;
		Collection<Position> usedPositions;
		Position position;
		result = new ModelAndView("position/list");
		positions = this.positionService.findAll();
		position = this.positionService.findOne(varId);
		usedPositions = this.positionService.getUsedPositions();

		if (usedPositions.contains(position))
			result.addObject("message", "position.delete.error");
		else
			try {
				this.positionService.delete(position);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.commit.error");
			}
		result.addObject("positions", positions);
		result.addObject("requestURI", "position/administrator/list.do");

		return result;
	}
	//Delete POST

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Position position, final BindingResult binding) {
		ModelAndView result;
		Collection<Position> usedPositions;
		usedPositions = this.positionService.getUsedPositions();

		if (usedPositions.contains(position))
			result = this.createEditModelAndView(position, "position.enrolment.error");
		else
			try {
				this.positionService.delete(position);
				result = new ModelAndView("redirect:/position/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(position, "position.delete.error");
			}
		return result;
	}

	//Displaying

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		final ModelAndView result;
		final Position position = this.positionService.findOne(varId);

		result = new ModelAndView("position/display");
		result.addObject("position", position);
		result.addObject("requestURI", "position/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Position position) {
		ModelAndView result;

		result = this.createEditModelAndView(position, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Position position, final String messageCode) {
		ModelAndView result;
		final Collection<Position> positions = this.positionService.findAll();

		result = new ModelAndView("position/edit");
		result.addObject("position", position);
		result.addObject("positions", positions);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "position/administrator/edit.do");

		return result;

	}
}
