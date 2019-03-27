
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MiscellaneousRecordRepository;
import domain.Brotherhood;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed repository ---------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService					actorService;

	@Autowired
	private Validator						validator;


	//Simple CRUD Methods --------------------------------

	public MiscellaneousRecord create() {

		//Creating entity
		final MiscellaneousRecord ir = new MiscellaneousRecord();
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		ir.setBrotherhood(b);
		return ir;
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final int id) {
		Assert.notNull(id);

		return this.miscellaneousRecordRepository.findOne(id);
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == miscellaneousRecord.getBrotherhood().getId());

		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		return saved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == miscellaneousRecord.getBrotherhood().getId());

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}
	public MiscellaneousRecord reconstruct(final MiscellaneousRecord mr, final BindingResult binding) {
		MiscellaneousRecord result;

		if (mr.getId() == 0)
			result = this.create();
		else
			result = this.miscellaneousRecordRepository.findOne(mr.getId());
		result.setTitle(mr.getTitle());
		result.setDescription(mr.getDescription());
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
	//The miscellaneous records from a  brotherhood
	public Collection<MiscellaneousRecord> miscellaneousRecordsfromBrotherhood(final int brotherhoodId) {
		return this.miscellaneousRecordRepository.miscellaneousRecordsfromBrotherhood(brotherhoodId);
	}

	public void flush() {
		this.miscellaneousRecordRepository.flush();
	}
}
