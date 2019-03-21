
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LegalRecordRepository;
import domain.Brotherhood;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	//Managed repository ---------------------------------

	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public LegalRecord create() {

		//Creating entity
		final LegalRecord ir = new LegalRecord();
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		ir.setBrotherhood(b);
		return ir;
	}

	public Collection<LegalRecord> findAll() {
		return this.legalRecordRepository.findAll();
	}

	public LegalRecord findOne(final int id) {
		Assert.notNull(id);

		return this.legalRecordRepository.findOne(id);
	}

	public LegalRecord save(final LegalRecord legalRecord) {
		Assert.notNull(legalRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == legalRecord.getBrotherhood().getId());

		final LegalRecord saved = this.legalRecordRepository.save(legalRecord);

		return saved;
	}

	public void delete(final LegalRecord legalRecord) {
		Assert.notNull(legalRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == legalRecord.getBrotherhood().getId());

		this.legalRecordRepository.delete(legalRecord);
	}
	//Reconstruct

	public LegalRecord reconstruct(final LegalRecord lr, final BindingResult binding) {
		LegalRecord result;

		if (lr.getId() == 0)
			result = this.create();
		else
			result = this.legalRecordRepository.findOne(lr.getId());
		result.setTitle(lr.getTitle());
		result.setDescription(lr.getDescription());
		result.setLegalName(lr.getLegalName());
		result.setVatNumber(lr.getVatNumber());
		result.setApplicableLaws(lr.getApplicableLaws());
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
	//The legal records from a  brotherhood
	public Collection<LegalRecord> legalRecordsfromBrotherhood(final int brotherhoodId) {
		return this.legalRecordRepository.legalRecordsfromBrotherhood(brotherhoodId);
	}
}
