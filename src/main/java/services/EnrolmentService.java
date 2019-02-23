
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

	//Supporting service

	@Autowired
	private ActorService		actorService;


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

		if (e.getId() == 0)
			e.setMoment(new Date(System.currentTimeMillis() - 1));

		//Assertion to make sure that the brotherhood has an area selected.
		Assert.isTrue(e.getBrotherhood().getArea() != null);

		if (e.getBrotherhoodToMember() == true)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getBrotherhood().getId());
		if (e.getBrotherhoodToMember() == false)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getMember().getId());

		final Enrolment saved = this.enrolmentRepository.save(e);
		return saved;
	}

	public void delete(final Enrolment e) {
		Assert.notNull(e);

		if (e.getBrotherhoodToMember() == true)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getBrotherhood().getId());
		if (e.getBrotherhoodToMember() == false)
			Assert.isTrue(this.actorService.findByPrincipal().getId() == e.getMember().getId());

		this.enrolmentRepository.delete(e);
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

	// A histogram of positions.
	public int[] histogramOfPositions() {
		return this.enrolmentRepository.histogramOfPositions();
	}
}
