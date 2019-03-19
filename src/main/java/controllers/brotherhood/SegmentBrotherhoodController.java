
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SegmentService;
import controllers.AbstractController;
import domain.Segment;

@Controller
@RequestMapping("segment/brotherhood")
public class SegmentBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private SegmentService	segmentService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Segment> segments;

		segments = this.segmentService.getSegmentsForParade(varId);

		result = new ModelAndView("segment/list");
		result.addObject("segments", segments);
		result.addObject("requestURI", "segment/brotherhood/list.do?varId=" + varId);

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Segment segment;

		segment = this.segmentService.create();
		result = this.createEditModelAndView(segment);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Segment segment, final BindingResult binding) {
		ModelAndView result;

		try {
			segment = this.segmentService.reconstruct(segment, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(segment, "segment.commit.error");
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(segment);
		else
			try {
				this.segmentService.save(segment);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(segment, "segment.commit.error");
			}
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		result = new ModelAndView("segment/list");
		final Collection<Segment> segments = this.segmentService.getSegmentsForParade(varId);

		try {
			for (final Segment s : segments)
				this.segmentService.delete(s);

			result = new ModelAndView("redirect:/segment/brotherhood/list.do?varId=" + varId);

		} catch (final Throwable oops) {
			result.addObject("message", "segment.delete.error");
			result.addObject("segments", segments);
			result.addObject("requestURI", "segment/brotherhood/list.do?varId=" + varId);
		}
		return result;
	}

	//Display 

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		Segment segment;

		segment = this.segmentService.findOne(varId);

		result = new ModelAndView("segment/display");
		result.addObject("segment", segment);
		result.addObject("requestURI", "segment/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Segment segment) {
		ModelAndView result;

		result = this.createEditModelAndView(segment, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Segment segment, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("segment/edit");
		result.addObject("segment", segment);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "segment/brotherhood/edit.do");

		return result;

	}

}
