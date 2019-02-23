/*
 * AdministratorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import java.util.ArrayList;
import java.util.Arrays;
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
import services.AdministratorService;
import services.AreaService;
import services.BrotherhoodService;
import services.EnrolmentService;
import services.FinderService;
import services.MemberService;
import services.ProcessionService;
import services.RequestService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Member;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	//Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private ProcessionService		processionService;

	@Autowired
	private EnrolmentService		enrolmentService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private FinderService			finderService;


	//Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;
		Administrator administrator;

		administrator = this.administratorService.create();
		result = this.createEditModelAndView(administrator);

		return result;
	}

	//Compute score

	@RequestMapping(value = "/computeScore", method = RequestMethod.GET)
	public ModelAndView computeScore() {
		final ModelAndView result;

		//TODO Llamar al computeScore una vez esté implementado
		//this.endorsementService.computeScoreForAll();

		result = new ModelAndView("redirect:/welcome/index.do");

		return result;
	}

	//Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView result;
		Administrator administrator;
		administrator = (Administrator) this.actorService.findByPrincipal();
		Assert.notNull(administrator);
		result = this.createEditModelAndView(administrator);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(administrator);
		else
			try {
				this.actorService.hashPassword(administrator);
				this.actorService.save(administrator);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "actor.commit.error");
			}
		return result;
	}

	//Dashboard

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;

		result = new ModelAndView("administrator/dashboard");

		result.addObject("minMaxAvgStddevMemberPerBrotherhood", Arrays.toString(this.memberService.minMaxAvgStddevMemberPerBrotherhood()));
		result.addObject("membersWithApprovedRequestsThanAvg", this.memberService.membersWithApprovedRequestsThanAvg());
		result.addObject("largestBrotherhoods", this.brotherhoodService.largestBrotherhoods());
		result.addObject("smallestBrotherhoods", this.brotherhoodService.smallestBrotherhoods());
		//TODO La query que viene a continuación como la mostramos en el dash? Porque ni zorra
		//result.addObject("ratioRequestByStatus", this.requestService.ratioRequestsByStatus(int id));
		result.addObject("ratioRequestsByStatus", this.requestService.ratioRequestsByStatus());
		result.addObject("processionsBefore30Days", this.processionService.processionsBefore30Days());
		result.addObject("histogramOfPositions", Arrays.toString(this.enrolmentService.histogramOfPositions()));
		//TODO La query que viene a continuación como la mostramos en el dash? Porque ni zorra
		//result.addObject("ratioAndCountOfBrotherhoodsByArea", this.areaRepository.ratioAndCountOfBrotherhoodsByArea(id));
		result.addObject("minMaxAvgAndStddevOfBrotherhoodsByArea", Arrays.toString(this.areaService.minMaxAvgAndStddevOfBrotherhoodsByArea()));
		//TODO Las querys que vienen a continuasión las tiene que aser todavia serjio
		result.addObject("minMaxAvgStddevResultsFinders", Arrays.toString(this.finderService.minMaxAvgStddevResultsFinders()));
		result.addObject("ratioEmptyVersusNonEmptyFinders", this.finderService.ratioEmptyVersusNonEmptyFinders());

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;
	}
	//Listing suspicious actors

	@RequestMapping(value = "/spammerList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Actor> actors = new ArrayList<Actor>();
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.spammerBrotherhoods();
		actors.addAll(brotherhoods);
		final Collection<Administrator> admins = this.administratorService.spammerAdministrators();
		actors.addAll(admins);
		final Collection<Member> members = this.memberService.spammerMembers();
		actors.addAll(members);

		result = new ModelAndView("administrator/spammerList");
		result.addObject("actors", actors);
		result.addObject("requestURI", "administrator/spammerList.do");

		return result;
	}

	//Ban and unban actors

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int varId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findOne(varId);

		if (actor.isSpammer() == true)
			this.actorService.BanOrUnban(actor.getId());

		result = new ModelAndView("redirect:/administrator/spammerList.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView result;

		result = this.createEditModelAndView(administrator, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("administrator/edit");
		result.addObject("administrator", administrator);
		result.addObject("message", messageCode);
		result.addObject("requestURI", "administrator/edit.do");

		return result;

	}
}
