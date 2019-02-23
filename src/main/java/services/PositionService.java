
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PositionRepository;
import security.Authority;
import domain.Position;

@Service
@Transactional
public class PositionService {

	//Managed repository

	@Autowired
	private PositionRepository	positionRepository;

	@Autowired
	private ActorService		actorService;


	//Supporting services --------------------------------

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
		//		this.actorService.checkSpam(saved.getSystemName());
		//		this.actorService.checkSpam(saved.getBanner());
		//		this.actorService.checkSpam(saved.getWelcomeEN());
		//		this.actorService.checkSpam(saved.getWelcomeES());
		return saved;
	}
	public void delete(final Position position) {
		Assert.notNull(position);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);
		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		this.positionRepository.delete(position);
	}

	//Other methods

	//Returns the used positions
	public Collection<Position> getUsedPositions() {
		return this.positionRepository.getUsedPositions();
	}
}
