
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LinkRecordService;
import domain.LinkRecord;

@Controller
@RequestMapping("linkRecord")
public class LinkRecordController extends AbstractController {

	//Services

	@Autowired
	private LinkRecordService	linkRecordService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int linkRecordId) {
		ModelAndView result;
		LinkRecord linkRecord;

		linkRecord = this.linkRecordService.findOne(linkRecordId);

		result = new ModelAndView("linkRecord/display");
		result.addObject("linkRecord", linkRecord);
		result.addObject("requestURI", "linkRecord/display.do");

		return result;
	}
	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<LinkRecord> linkRecords;

		linkRecords = this.linkRecordService.findAll();

		result = new ModelAndView("linkRecord/list");
		result.addObject("linkRecords", linkRecords);
		result.addObject("requestURI", "linkRecord/list.do");

		return result;
	}
}
