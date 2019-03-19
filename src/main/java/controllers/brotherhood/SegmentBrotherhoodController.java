
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
import services.SegmentService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Parade;
import domain.Segment;

@Controller
@RequestMapping("segment/brotherhood")
public class SegmentBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private SegmentService	segmentService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ParadeService	paradeService;


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
	public ModelAndView create(@RequestParam final int varId) {
		final ModelAndView result;
		Segment segment;

		segment = this.segmentService.create(varId);
		result = this.createEditModelAndView(segment);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Segment segment, final BindingResult binding) {
		ModelAndView result;
		try {
			if (segment.getId() != 0)
				segment = this.segmentService.reconstruct(segment, binding);
			else
				segment = this.segmentService.reconstructOrdered(segment, binding);
		} catch (final ValidationException oops) {
			return this.createEditModelAndView(segment);
		} catch (final Throwable oops) {
			return this.createEditModelAndView(segment, "segment.commit.error");
		}
		try {
			this.segmentService.save(segment);
			result = new ModelAndView("redirect:/segment/brotherhood/list.do?varId=" + segment.getParade().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(segment, "segment.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		ModelAndView result;
		result = new ModelAndView("segment/list");
		final Segment segment = this.segmentService.findOne(varId);
		final Segment lastSegment = this.segmentService.getLastSegment(segment.getParade().getId());
		final Collection<Segment> segments = this.segmentService.getSegmentsForParade(segment.getParade().getId());

		//Assertion the user has the correct privilege
		if (segment.getParade().getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");
		try {
			Assert.isTrue(segment.getId() == lastSegment.getId());
			result = this.createEditModelAndView(segment);
		} catch (final Throwable oops) {
			result.addObject("message", "segment.edit.error");
		}
		result.addObject("segments", segments);
		return result;
	}

	//Deleting

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		result = new ModelAndView("segment/list");
		final Collection<Segment> segments = this.segmentService.getSegmentsForParade(varId);

		try {
			this.segmentService.delete(varId);
			result = new ModelAndView("redirect:/parade/brotherhood/list.do");

		} catch (final Throwable oops) {
			result.addObject("message", "segment.delete.error");
			result.addObject("segments", segments);
			result.addObject("requestURI", "/parade/brotherhood/list.do");
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
		Collection<Parade> parades;
		final Collection<Segment> orderedSegments = this.segmentService.getOrderedSegmentsForParade(segment.getParade().getId());
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		parades = this.paradeService.finalParadesForBrotherhood(b.getId());

		result = new ModelAndView("segment/edit");
		result.addObject("segment", segment);
		result.addObject("parades", parades);
		result.addObject("orderedSegments", orderedSegments);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "segment/brotherhood/edit.do");

		return result;

	}

}
