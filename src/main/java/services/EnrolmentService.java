
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EnrolmentRepository;
import security.Authority;
import domain.Actor;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;

@Service
@Transactional
public class EnrolmentService {

	//Managed service

	@Autowired
	private EnrolmentRepository	enrolmentRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Enrolment create(final int actorId) {

		final Actor sender = this.actorService.findByPrincipal();
		final Actor receiver = this.actorService.findOne(actorId);
		final Enrolment e = new Enrolment();
		final Authority authMemb = new Authority();
		final Authority authBrotherh = new Authority();
		authMemb.setAuthority(Authority.MEMBER);
		authBrotherh.setAuthority(Authority.BROTHERHOOD);
		e.setMoment(new Date(System.currentTimeMillis() - 1));
		if (sender.getUserAccount().getAuthorities().contains(authBrotherh) && receiver.getUserAccount().getAuthorities().contains(authMemb)) {
			e.setBrotherhood((Brotherhood) sender);
			e.setBrotherhoodToMember(true);
			e.setMember((Member) receiver);
		} else {
			e.setBrotherhood((Brotherhood) receiver);
			e.setBrotherhoodToMember(false);
			e.setMember((Member) sender);
		}

		return e;
	}
	public Enrolment findOne(final int id) {
		Assert.notNull(id);
		return this.enrolmentRepository.findOne(id);
	}

	public Collection<Enrolment> findAll() {
		return this.enrolmentRepository.findAll();
	}

	public Enrolment save(final Enrolment e) {
		Assert.notNull(e);

		if (e.getId() == 0) {
			Assert.isTrue(e.getPosition() != null);
			e.setMoment(new Date(System.currentTimeMillis() - 1));
		}

		//Assertion the user has the correct privilege
		Assert.isTrue(e.getBrotherhood().getId() == this.actorService.findByPrincipal().getId() || e.getMember().getId() == this.actorService.findByPrincipal().getId());

		//Assertion to make sure that the dropOutMoment is after the initial moment if it exists.
		if (e.getDropOutMoment() != null)
			Assert.isTrue(e.getDropOutMoment().after(e.getMoment()));

		//Assertion to make sure that the brotherhood has an area selected.
		Assert.isTrue(e.getBrotherhood().getArea() != null);

		final Enrolment saved = this.enrolmentRepository.save(e);
		return saved;
	}

	public Enrolment saveFromMember(final Enrolment e) {
		Assert.notNull(e);

		if (e.getId() == 0)
			e.setMoment(new Date(System.currentTimeMillis() - 1));

		//Assertion the correction has the correct privilege
		Assert.isTrue(e.getMember().getId() == this.actorService.findByPrincipal().getId());

		//Assertion there is not another enrolment for that brotherhood
		Assert.isTrue(!this.brotherhoodService.nonEnrolmentableBrotherhoods(this.actorService.findByPrincipal().getId()).contains(e.getBrotherhood()));

		final Enrolment saved = this.enrolmentRepository.save(e);
		return saved;
	}

	public Enrolment saveFromBrotherhood(final Enrolment e) {
		Assert.notNull(e);
		Assert.notNull(e.getBrotherhood().getArea());

		if (e.getId() == 0)
			e.setMoment(new Date(System.currentTimeMillis() - 1));

		//Assertion the correction has the correct privilege
		Assert.isTrue(e.getBrotherhood().getId() == this.actorService.findByPrincipal().getId());

		//Assertion has a position
		Assert.isTrue(e.getPosition() != null);

		//Assertion there is not another enrolment for that brotherhood
		Assert.isTrue(!this.brotherhoodService.nonEnrolmentableMembers(this.actorService.findByPrincipal().getId()).contains(e.getMember()));

		final Enrolment saved = this.enrolmentRepository.save(e);

		//Sending notification to the member
		if (saved.getBrotherhood().getId() == this.actorService.findByPrincipal().getId() && saved.getBrotherhoodToMember() == true)
			this.messageService.newEnrolmentNotification(saved);

		return saved;
	}

	public Enrolment saveFromAdmin(final Enrolment e) {
		Assert.notNull(e);

		final Enrolment saved = this.enrolmentRepository.save(e);

		return saved;
	}

	public void delete(final Enrolment e) {
		Assert.notNull(e);

		//Assertion enrolment actor has the correct privilege
		Assert.isTrue(e.getBrotherhood().getId() == this.actorService.findByPrincipal().getId() || e.getMember().getId() == this.actorService.findByPrincipal().getId());

		//Assertion enrolment e has drop out moment
		Assert.isTrue(e.getDropOutMoment() != null);

		this.enrolmentRepository.delete(e);
	}

	public void drop(final Enrolment e) {
		Assert.notNull(e);

		//Assertion the user has the correct privilege
		Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getBrotherhood().getId() || e.getMember().getId() == this.actorService.findByPrincipal().getId());

		e.setDropOutMoment(new Date(System.currentTimeMillis() - 1));

		//Sending notification to the brotherhood
		if (e.getMember().getId() == this.actorService.findByPrincipal().getId())
			this.messageService.dropOutEnrolmentNotification(e);

		this.enrolmentRepository.save(e);
	}
	//Reconstruct

	public Enrolment reconstruct(final Enrolment enrolment, final BindingResult binding) {
		Enrolment result;

		if (enrolment.getId() == 0)
			result = this.create(enrolment.getMember().getId());
		else
			result = this.enrolmentRepository.findOne(enrolment.getId());

		result.setPosition(enrolment.getPosition());

		this.validator.validate(result, binding);

		//Assertion the correction has the correct privilege
		Assert.isTrue(result.getBrotherhood().getId() == this.actorService.findByPrincipal().getId());

		//Assertion has a position
		Assert.isTrue(result.getPosition() != null);

		//Assertion there is not another enrolment for that brotherhood
		Assert.isTrue(!this.brotherhoodService.nonEnrolmentableMembers(this.actorService.findByPrincipal().getId()).contains(result.getMember()));

		return result;

	}

	//Other methods

	//Returns the received enrolments for a certain member
	public Collection<Enrolment> receivedEnrolmentsFromMember(final int actorId) {
		return this.enrolmentRepository.receivedEnrolmentsFromMember(actorId);
	}
	//Returns the received enrolments for a certain brotherhood
	public Collection<Enrolment> receivedEnrolmentsFromBrotherhood(final int actorId) {
		return this.enrolmentRepository.receivedEnrolmentsFromBrotherhood(actorId);
	}

	//Returns the enrolments given a position
	public Collection<Enrolment> getEnrolmentsFromAPosition(final int positionId) {
		return this.enrolmentRepository.getEnrolmentsFromAPosition(positionId);
	}

	// A histogram of positions.
	public Collection<Integer> histogramOfPositions() {
		return this.enrolmentRepository.histogramOfPositions();
	}

}
