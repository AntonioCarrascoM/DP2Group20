
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("miscellaneousRecord/brotherhood")
public class MiscellaneousRecordBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private ActorService				actorService;


	//Ancillary attributes

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<MiscellaneousRecord> miscellaneousRecords;

		miscellaneousRecords = this.miscellaneousRecordService.miscellaneousRecordsfromBrotherhood(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("miscellaneousRecord/list");
		result.addObject("miscellaneousRecord", miscellaneousRecords);
		result.addObject("requestURI", "miscellaneousRecord/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(varId);
		Assert.notNull(miscellaneousRecord);
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			miscellaneousRecord = this.miscellaneousRecordService.reconstruct(miscellaneousRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}

		try {
			this.miscellaneousRecordService.save(miscellaneousRecord);
			result = new ModelAndView("redirect:/miscellaneousRecord/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);

		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			miscellaneousRecord = this.miscellaneousRecordService.reconstruct(miscellaneousRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}
		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "miscellaneousRecord/brotherhood/edit.do");

		return result;

	}
}
