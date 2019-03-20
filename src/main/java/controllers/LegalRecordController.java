
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LegalRecordService;
import domain.LegalRecord;

@Controller
@RequestMapping("legalRecord")
public class LegalRecordController extends AbstractController {

	//Services

	@Autowired
	private LegalRecordService	legalRecordService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int legalRecordId) {
		ModelAndView result;
		LegalRecord legalRecord;

		legalRecord = this.legalRecordService.findOne(legalRecordId);

		result = new ModelAndView("legalRecord/display");
		result.addObject("legalRecord", legalRecord);
		result.addObject("requestURI", "legalRecord/display.do");

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<LegalRecord> legalRecords;

		legalRecords = this.legalRecordService.findAll();

		result = new ModelAndView("legalRecord/list");
		result.addObject("legalRecords", legalRecords);
		result.addObject("requestURI", "legalRecord/list.do");

		return result;
	}
}
