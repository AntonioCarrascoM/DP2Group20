
package controllers.brotherhood;

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
import services.ProcessionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Procession;

@Controller
@RequestMapping("procession/brotherhood")
public class ProcessionBrotherhoodController extends AbstractController {

	//Services

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private ActorService		actorService;


	//Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Procession> processions;
		Brotherhood brotherhood;

		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		processions = brotherhood.getProcessions();

		result = new ModelAndView("procession/list");
		result.addObject("processions", processions);
		result.addObject("requestURI", "procession/brotherhood/list.do");

		return result;
	}

	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Procession procession;

		procession = this.processionService.create();
		result = this.createEditModelAndView(procession);

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int varId) {
		final ModelAndView result;
		Procession procession = this.processionService.findOne(varId);

		if (procession.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			return new ModelAndView("redirect:/welcome/index.do");

		procession = this.processionService.findOne(varId);

		Assert.notNull(procession);
		result = this.createEditModelAndView(procession);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Procession procession, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(procession);
		else
			try {

				if (procession.getFinalMode() == true) {
					procession.setFinalMode(false);
					this.processionService.save(procession, true);
				} else
					this.processionService.save(procession, false);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Procession procession, final BindingResult binding) {
		ModelAndView result;

		if (procession.getBrotherhood().getId() != this.actorService.findByPrincipal().getId())
			result = this.createEditModelAndView(procession, "procession.delete.error");
		else
			try {
				this.processionService.delete(procession);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(procession, "procession.commit.error");
			}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int varId) {
		ModelAndView result;
		Collection<Procession> processions;
		Brotherhood brotherhood;
		Procession procession;

		result = new ModelAndView("procession/list");
		brotherhood = (Brotherhood) this.actorService.findByPrincipal();
		processions = brotherhood.getProcessions();

		procession = this.processionService.findOne(varId);

		if (procession.getBrotherhood().getId() != this.actorService.findByPrincipal().getId()) {
			result = new ModelAndView("redirect:/welcome/index.do");
			return result;
		} else
			try {
				this.processionService.delete(procession);
				result.addObject("processions", processions);
				result.addObject("requestURI", "procession/list.do");
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:list.do");
			}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Procession procession) {
		ModelAndView result;

		result = this.createEditModelAndView(procession, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Procession procession, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("procession/edit");
		result.addObject("procession", procession);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "procession/brotherhood/edit.do");

		return result;

	}

}
