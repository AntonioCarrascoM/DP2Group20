
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.InceptionRecordService;
import domain.InceptionRecord;

@Controller
@RequestMapping("inceptionRecord")
public class InceptionRecordController extends AbstractController {

	//Services

	@Autowired
	private InceptionRecordService	inceptionRecordService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int inceptionRecordId) {
		ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);

		result = new ModelAndView("inceptionRecord/display");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("requestURI", "inceptionRecord/display.do");

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<InceptionRecord> inceptionRecords;

		inceptionRecords = this.inceptionRecordService.findAll();

		result = new ModelAndView("inceptionRecord/list");
		result.addObject("inceptionRecords", inceptionRecords);
		result.addObject("requestURI", "inceptionRecord/list.do");

		return result;
	}
}
