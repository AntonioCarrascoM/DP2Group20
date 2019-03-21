
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.PeriodRecordService;
import controllers.AbstractController;
import domain.PeriodRecord;

@Controller
@RequestMapping("periodRecord/brotherhood")
public class PeriodRecordBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private PeriodRecordService	periodRecordService;

	@Autowired
	private ActorService		actorService;


	//Ancillary attributes

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<PeriodRecord> periodRecords;

		periodRecords = this.periodRecordService.periodRecordsfromBrotherhood(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("periodRecord/list");
		result.addObject("periodRecord", periodRecords);
		result.addObject("requestURI", "periodRecord/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.create();
		result = this.createEditModelAndView(periodRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(varId);
		Assert.notNull(periodRecord);
		result = this.createEditModelAndView(periodRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			periodRecord = this.periodRecordService.reconstruct(periodRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
		}
		try {
			this.periodRecordService.save(periodRecord);
			result = new ModelAndView("redirect:/periodRecord/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int periodRecordId) {
		ModelAndView result;
		final PeriodRecord periodRecord = this.periodRecordService.findOne(periodRecordId);

		try {
			this.periodRecordService.delete(periodRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			periodRecord = this.periodRecordService.reconstruct(periodRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
		}
		try {
			this.periodRecordService.delete(periodRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(periodRecord, "periodRecord.commit.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final PeriodRecord periodRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(periodRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PeriodRecord periodRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("periodRecord/edit");
		result.addObject("periodRecord", periodRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "periodRecord/brotherhood/edit.do");

		return result;

	}
}
