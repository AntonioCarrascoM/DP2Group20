
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
import services.LegalRecordService;
import controllers.AbstractController;
import domain.LegalRecord;

@Controller
@RequestMapping("legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private ActorService		actorService;


	//Ancillary attributes

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<LegalRecord> legalRecords;

		legalRecords = this.legalRecordService.legalRecordsfromBrotherhood(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("legalRecord/list");
		result.addObject("legalRecords", legalRecords);
		result.addObject("requestURI", "legalRecord/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		LegalRecord legalRecord;

		legalRecord = this.legalRecordService.create();
		result = this.createEditModelAndView(legalRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		LegalRecord legalRecord;

		legalRecord = this.legalRecordService.findOne(varId);
		Assert.notNull(legalRecord);

		if (legalRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		result = this.createEditModelAndView(legalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			legalRecord = this.legalRecordService.reconstruct(legalRecord, binding);
		} catch (final ValidationException oops) {
			return result = this.createEditModelAndView(legalRecord);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalRecord, "legalRecord.commit.error");
		}

		try {
			this.legalRecordService.save(legalRecord);
			result = new ModelAndView("redirect:/legalRecord/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalRecord, "legalRecord.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		final LegalRecord legalRecord = this.legalRecordService.findOne(varId);

		if (legalRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		try {
			this.legalRecordService.delete(legalRecord);
			result = new ModelAndView("redirect:/legalRecord/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(legalRecord, "legalRecord.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;

		legalRecord = this.legalRecordService.findOne(legalRecord.getId());

		if (legalRecord.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(legalRecord, "legalRecord.delete.error");
		else
			try {
				this.legalRecordService.delete(legalRecord);
				result = new ModelAndView("redirect:/legalRecord/brotherhood/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(legalRecord, "legalRecord.commit.error");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final LegalRecord legalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(legalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LegalRecord legalRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("legalRecord/edit");
		result.addObject("legalRecord", legalRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "legalRecord/brotherhood/edit.do");

		return result;

	}
}
