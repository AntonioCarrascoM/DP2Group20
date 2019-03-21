
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PeriodRecordRepository;
import domain.Brotherhood;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	//Managed repository ---------------------------------

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public PeriodRecord create() {

		//Creating entity
		final PeriodRecord ir = new PeriodRecord();
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		ir.setBrotherhood(b);
		return ir;
	}

	public Collection<PeriodRecord> findAll() {
		return this.periodRecordRepository.findAll();
	}

	public PeriodRecord findOne(final int id) {
		Assert.notNull(id);

		return this.periodRecordRepository.findOne(id);
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == periodRecord.getBrotherhood().getId());

		final PeriodRecord saved = this.periodRecordRepository.save(periodRecord);

		return saved;
	}

	public void delete(final PeriodRecord periodRecord) {
		Assert.notNull(periodRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == periodRecord.getBrotherhood().getId());

		this.periodRecordRepository.delete(periodRecord);
	}

	//Reconstruct

	public PeriodRecord reconstruct(final PeriodRecord pr, final BindingResult binding) {
		PeriodRecord result;

		if (pr.getId() == 0)
			result = this.create();
		else
			result = this.periodRecordRepository.findOne(pr.getId());
		result.setTitle(pr.getTitle());
		result.setDescription(pr.getDescription());
		result.setStartYear(pr.getStartYear());
		result.setEndYear(pr.getEndYear());
		result.setPhotos(pr.getPhotos());
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
	//The period records from a  brotherhood
	public Collection<PeriodRecord> periodRecordsfromBrotherhood(final int brotherhoodId) {
		return this.periodRecordRepository.periodRecordsfromBrotherhood(brotherhoodId);
	}
}
