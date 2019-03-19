
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SegmentRepository;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	//Managed repository

	@Autowired
	private SegmentRepository	segmentRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Segment create() {
		final Segment s = new Segment();

		return s;
	}
	public Segment findOne(final int id) {
		Assert.notNull(id);

		return this.segmentRepository.findOne(id);
	}

	public Collection<Segment> findAll() {
		return this.segmentRepository.findAll();
	}

	public Segment save(final Segment s) {
		Assert.notNull(s);

		//Assertion the origin date is before destination date
		Assert.isTrue(s.getOriginDate().before(s.getDestinationDate()));

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == s.getParade().getBrotherhood().getId());

		final Segment saved = this.segmentRepository.save(s);

		return saved;
	}

	public void delete(final Segment s) {
		Assert.notNull(s);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == s.getParade().getBrotherhood().getId());

		this.segmentRepository.delete(s);
	}

	//Reconstruct

	public Segment reconstruct(final Segment s, final BindingResult binding) {
		Segment result;

		if (s.getId() == 0)
			result = this.create();
		else
			result = this.segmentRepository.findOne(s.getId());
		result.setOriginCoordX(s.getOriginCoordX());
		result.setOriginCoordY(s.getOriginCoordY());
		result.setDestinationCoordX(s.getDestinationCoordX());
		result.setDestinationCoordY(s.getDestinationCoordY());
		result.setOriginDate(s.getOriginDate());
		result.setDestinationDate(s.getDestinationDate());
		result.setParade(s.getParade());

		this.validator.validate(result, binding);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == s.getParade().getBrotherhood().getId());

		//Assertion the origin date is before destination date
		Assert.isTrue(s.getOriginDate().before(s.getDestinationDate()));

		return result;
	}

	//Other methods

	public Collection<Segment> getSegmentsForParade(final int paradeId) {
		return this.segmentRepository.getSegmentsForParade(paradeId);
	}
}
