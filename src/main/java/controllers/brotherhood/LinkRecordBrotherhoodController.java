
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
import services.LinkRecordService;
import controllers.AbstractController;
import domain.LinkRecord;

@Controller
@RequestMapping("linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private LinkRecordService	linkRecordService;

	@Autowired
	private ActorService		actorService;


	//Ancillary attributes

	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<LinkRecord> linkRecords;

		linkRecords = this.linkRecordService.linkRecordsfromBrotherhood(this.actorService.findByPrincipal().getId());

		result = new ModelAndView("linkRecord/list");
		result.addObject("linkRecord", linkRecords);
		result.addObject("requestURI", "linkRecord/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		LinkRecord linkRecord;

		linkRecord = this.linkRecordService.create();
		result = this.createEditModelAndView(linkRecord);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		LinkRecord linkRecord;

		linkRecord = this.linkRecordService.findOne(varId);
		Assert.notNull(linkRecord);
		result = this.createEditModelAndView(linkRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			linkRecord = this.linkRecordService.reconstruct(linkRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
		}
		try {
			this.linkRecordService.save(linkRecord);
			result = new ModelAndView("redirect:/linkRecord/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
		}
		return result;
	}
	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int linkRecordId) {
		ModelAndView result;
		final LinkRecord linkRecord = this.linkRecordService.findOne(linkRecordId);

		try {
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;

		try {
			linkRecord = this.linkRecordService.reconstruct(linkRecord, binding);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
		}
		try {
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/application/brotherhood/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(linkRecord, "linkRecord.commit.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final LinkRecord linkRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(linkRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final LinkRecord linkRecord, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("linkRecord/edit");
		result.addObject("linkRecord", linkRecord);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "linkRecord/brotherhood/edit.do");

		return result;

	}
}
