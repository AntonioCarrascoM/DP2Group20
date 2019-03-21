
package controllers;

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
	public ModelAndView display(@RequestParam final int varId) {
		ModelAndView result;
		InceptionRecord inceptionRecord;

		inceptionRecord = this.inceptionRecordService.findOne(varId);

		result = new ModelAndView("inceptionRecord/display");
		result.addObject("inceptionRecord", inceptionRecord);
		result.addObject("requestURI", "inceptionRecord/display.do");

		return result;
	}
}
