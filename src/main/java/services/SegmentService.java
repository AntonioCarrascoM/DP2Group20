
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SegmentRepository;
import domain.Parade;
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
	private ParadeService		paradeService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Segment create(final int varId) {
		Parade parade;
		parade = this.paradeService.findOne(varId);
		final Segment s = new Segment();
		s.setParade(parade);

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

		//Assertion the parade is on final mode
		Assert.isTrue(!s.getParade().getFinalMode());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == s.getParade().getBrotherhood().getId());

		final Segment saved = this.segmentRepository.save(s);

		return saved;
	}

	public void delete(final int varId) {
		Assert.notNull(varId);
		final Parade p = this.paradeService.findOne(varId);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		//Assertion the parade is not on final mode
		Assert.isTrue(!p.getFinalMode());

		final Collection<Segment> segments = this.getSegmentsForParade(varId);

		if (segments.size() != 0)
			for (final Segment s : segments)
				this.segmentRepository.delete(s);
	}

	//Reconstruct (when it is edited)

	public Segment reconstruct(final Segment s, final BindingResult binding) {
		Segment result;

		result = this.segmentRepository.findOne(s.getId());

		result.setDestinationCoordX(s.getDestinationCoordX());
		result.setDestinationCoordY(s.getDestinationCoordY());
		result.setDestinationDate(s.getDestinationDate());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getParade().getBrotherhood().getId());

		//Assertion the parade is not on final mode
		Assert.isTrue(!result.getParade().getFinalMode());

		//Assertion the origin date is before destination date
		Assert.isTrue(result.getOriginDate().before(result.getDestinationDate()));

		return result;
	}

	//Reconstruct ordered (when it is created)

	public Segment reconstructOrdered(final Segment s, final BindingResult binding) {
		Segment result;
		final Segment lastSegment = this.getLastSegment(s.getParade().getId());

		if (lastSegment != null) {
			result = this.create(s.getParade().getId());
			result.setOriginCoordX(lastSegment.getDestinationCoordX());
			result.setOriginCoordY(lastSegment.getDestinationCoordY());
			result.setDestinationCoordX(s.getDestinationCoordX());
			result.setDestinationCoordY(s.getDestinationCoordY());
			result.setOriginDate(lastSegment.getDestinationDate());
			result.setDestinationDate(s.getDestinationDate());
		} else {
			result = this.create(s.getParade().getId());
			result.setOriginCoordX(s.getOriginCoordX());
			result.setOriginCoordY(s.getOriginCoordY());
			result.setDestinationCoordX(s.getDestinationCoordX());
			result.setDestinationCoordY(s.getDestinationCoordY());
			result.setOriginDate(s.getOriginDate());
			result.setDestinationDate(s.getDestinationDate());
		}
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getParade().getBrotherhood().getId());

		//Assertion the parade is not on final mode
		Assert.isTrue(!result.getParade().getFinalMode());

		//Assertion the origin date is before destination date
		Assert.isTrue(result.getOriginDate().before(result.getDestinationDate()));

		return result;

	}

	//Other methods

	//Returns the segments of a parade
	public Collection<Segment> getSegmentsForParade(final int paradeId) {
		return this.segmentRepository.getSegmentsForParade(paradeId);
	}

	//Returns the segments of a parade ordered by destination date, the last one is the first in the collection
	public Collection<Segment> getOrderedSegmentsForParade(final int paradeId) {
		return this.segmentRepository.getOrderedSegmentsForParade(paradeId);
	}

	//Returns the last segment of a certain parade
	public Segment getLastSegment(final int paradeId) {
		Segment lastSegment;
		final Collection<Segment> orderedSegments = this.getOrderedSegmentsForParade(paradeId);
		if (orderedSegments.isEmpty())
			return null;
		else
			lastSegment = orderedSegments.iterator().next();
		return lastSegment;
	}

	public void flush() {
		this.segmentRepository.flush();
	}

}
