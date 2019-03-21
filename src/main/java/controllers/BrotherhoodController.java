
package controllers;

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
import services.AreaService;
import services.BrotherhoodService;
import services.ChapterService;
import services.InceptionRecordService;
import services.LegalRecordService;
import services.LinkRecordService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;
import forms.FormObjectBrotherhood;

@Controller
@RequestMapping("brotherhood")
public class BrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private AreaService					areaService;

	@Autowired
	private ChapterService				chapterService;

	@Autowired
	private InceptionRecordService		inceptionRecordService;

	@Autowired
	private LegalRecordService			legalRecordService;

	@Autowired
	private PeriodRecordService			periodRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private LinkRecordService			linkRecordService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		FormObjectBrotherhood fob;

		fob = new FormObjectBrotherhood();
		result = this.createEditModelAndView(fob);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		Assert.notNull(brotherhood);
		result = this.editModelAndView(brotherhood);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "create")
	public ModelAndView save(@Valid final FormObjectBrotherhood fob, final BindingResult binding) {
		ModelAndView result;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.reconstruct(fob, binding);
		} catch (final Throwable oops) {
			return result = this.createEditModelAndView(fob, "brotherhood.reconstruct.error");
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(fob);

		else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(fob, "brotherhood.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Brotherhood brotherhood, final BindingResult binding) {
		ModelAndView result;

		try {
			brotherhood = this.brotherhoodService.reconstructPruned(brotherhood, binding);
		} catch (final Throwable oops) {
			return result = this.editModelAndView(brotherhood, "brotherhood.commit.error");
		}

		if (binding.hasErrors())
			result = this.editModelAndView(brotherhood);

		else
			try {
				this.brotherhoodService.save(brotherhood);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.editModelAndView(brotherhood, "brotherhood.commit.error");
			}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Brotherhood> brotherhoods;

		brotherhoods = this.brotherhoodService.findAll();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/list.do");

		return result;
	}

	//List by chapter
	@RequestMapping(value = "/listByChapter", method = RequestMethod.GET)
	public ModelAndView listByChapter(@RequestParam final int varId) {
		final ModelAndView result;
		final Collection<Brotherhood> brotherhoods;

		final Chapter chap = this.chapterService.findOne(varId);

		brotherhoods = chap.getArea().getBrotherhoods();

		result = new ModelAndView("brotherhood/list");
		result.addObject("brotherhoods", brotherhoods);
		result.addObject("requestURI", "brotherhood/listByChapter.do");

		return result;
	}

	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;

		final Brotherhood brotherhood = this.brotherhoodService.findOne(varId);

		final InceptionRecord inceptionRecord = this.inceptionRecordService.inceptionRecordfromBrotherhood(varId);
		final Collection<PeriodRecord> periodRecords = this.periodRecordService.periodRecordsfromBrotherhood(varId);
		final Collection<LinkRecord> linkRecords = this.linkRecordService.linkRecordsfromBrotherhood(varId);
		final Collection<MiscellaneousRecord> miscellaneousRecords = this.miscellaneousRecordService.miscellaneousRecordsfromBrotherhood(varId);
		final Collection<LegalRecord> legalRecords = this.legalRecordService.legalRecordsfromBrotherhood(varId);

		result = new ModelAndView("brotherhood/display");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("periodRecords", periodRecords);
		result.addObject("linkRecords", linkRecords);
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		result.addObject("legalRecords", legalRecords);
		result.addObject("brotherhood", brotherhood);
		result.addObject("requestURI", "brotherhood/display.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final FormObjectBrotherhood fob) {
		ModelAndView result;

		result = this.createEditModelAndView(fob, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FormObjectBrotherhood fob, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("brotherhood/create");
		result.addObject("fob", fob);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "brotherhood/create.do");

		return result;
	}

	protected ModelAndView editModelAndView(final Brotherhood brotherhood) {
		ModelAndView result;

		result = this.editModelAndView(brotherhood, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Brotherhood brotherhood, final String messageCode) {
		ModelAndView result;

		final Collection<Area> areas = this.areaService.findAll();

		result = new ModelAndView("brotherhood/edit");
		result.addObject("brotherhood", brotherhood);
		result.addObject("areas", areas);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "brotherhood/edit.do");

		return result;
	}
}
