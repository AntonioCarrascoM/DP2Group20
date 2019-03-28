
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import domain.Enrolment;
import domain.Position;

@Service
@Transactional
public class PositionService {

	//Managed repository

	@Autowired
	private PositionRepository	positionRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private EnrolmentService	enrolmentService;


	//Simple CRUD methods

	public Position create() {

		final Position position = new Position();
		return position;
	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position findOne(final int id) {
		Assert.notNull(id);

		return this.positionRepository.findOne(id);
	}

	public Position save(final Position position) {
		Assert.notNull(position);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		//Assertion that the user modifying this position has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		final Position saved = this.positionRepository.save(position);

		return saved;
	}
	public void delete(final Position position) {
		Assert.notNull(position);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		//Assertion that the position is not used
		final Collection<Position> usedPositions = this.getUsedPositions();
		Assert.isTrue(!usedPositions.contains(position));

		final Collection<Enrolment> enrolments = this.enrolmentService.getEnrolmentsFromAPosition(position.getId());
		if (!enrolments.isEmpty())
			for (final Enrolment e : enrolments) {
				e.setPosition(null);
				this.enrolmentService.saveFromAdmin(e);
			}

		this.positionRepository.delete(position);
	}

	//Other methods

	//Returns the used positions
	public Collection<Position> getUsedPositions() {
		return this.positionRepository.getUsedPositions();
	}

	// A histogram of positions.
	public Collection<String> histogramOfPositions1() {
		return this.positionRepository.histogramOfPositions1();
	}

	// A histogram of positions.
	public Collection<Integer> histogramOfPositions2() {
		return this.positionRepository.histogramOfPositions2();
	}

	public void flush() {
		this.positionRepository.flush();
	}

}
