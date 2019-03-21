
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PeriodRecordService;
import domain.PeriodRecord;

@Controller
@RequestMapping("periodRecord")
public class PeriodRecordController extends AbstractController {

	//Services

	@Autowired
	private PeriodRecordService	periodRecordService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		PeriodRecord periodRecord;

		periodRecord = this.periodRecordService.findOne(varId);

		result = new ModelAndView("periodRecord/display");
		result.addObject("periodRecord", periodRecord);
		result.addObject("requestURI", "periodRecord/display.do");

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int varId) {
		final ModelAndView result;
		Collection<PeriodRecord> periodRecords;

		periodRecords = this.periodRecordService.periodRecordsfromBrotherhood(varId);

		result = new ModelAndView("periodRecord/list");
		result.addObject("periodRecords", periodRecords);
		result.addObject("requestURI", "periodRecord/list.do");

		return result;
	}
}
