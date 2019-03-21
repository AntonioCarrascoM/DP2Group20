
package controllers.brotherhood;

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
import services.InceptionRecordService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.InceptionRecord;

@Controller
@RequestMapping("inceptionRecord/brotherhood")
public class InceptionRecordBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private InceptionRecordService	inceptionRecordService;

	@Autowired
	private ActorService			actorService;


	//Ancillary attributes

	//Options
	@RequestMapping(value = "/createOrDisplay", method = RequestMethod.GET)
	public ModelAndView createOrDisplay() {

		final Brotherhood b = ((Brotherhood) this.actorService.findByPrincipal());
		if (this.inceptionRecordService.inceptionRecordfromBrotherhood(b.getId()) == null)
			return this.create();
		else
			return this.display();
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.inceptionRecordfromBrotherhood(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("inceptionRecord/display");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("requestURI", "inceptionRecord/display.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.create();
		result = this.createEditModelAndView(inceptionRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.findOne(varId);
		Assert.notNull(inceptionRecord);

		if (inceptionRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(inceptionRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			inceptionRecord = this.inceptionRecordService.reconstruct(inceptionRecord, binding);
		} catch (final ValidationException oops) {
			return result = this.createEditModelAndView(inceptionRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.commit.error");
		}
		try {
			this.inceptionRecordService.save(inceptionRecord);
			result = new ModelAndView("redirect:/inceptionRecord/brotherhood/display.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(varId);

		if (inceptionRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.inceptionRecordService.delete(inceptionRecord);
			result = new ModelAndView("redirect:/welcome/index.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;

		inceptionRecord = this.inceptionRecordService.findOne(inceptionRecord.getId());

		if (inceptionRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.delete.error");
		else
			try {
				this.inceptionRecordService.delete(inceptionRecord);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(inceptionRecord, "inceptionRecord.commit.error");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(inceptionRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final InceptionRecord inceptionRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("inceptionRecord/edit");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "inceptionRecord/brotherhood/edit.do");

		return result;

	}
}
