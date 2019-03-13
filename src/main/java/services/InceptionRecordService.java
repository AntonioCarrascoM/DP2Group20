
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.InceptionRecordRepository;
import domain.Brotherhood;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	//Managed repository ---------------------------------

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	//Simple CRUD Methods --------------------------------

	public InceptionRecord create() {

		//Creating entity
		final InceptionRecord ir = new InceptionRecord();
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		ir.setBrotherhood(b);
		return ir;
	}

	public Collection<InceptionRecord> findAll() {
		return this.inceptionRecordRepository.findAll();
	}

	public InceptionRecord findOne(final int id) {
		Assert.notNull(id);

		return this.inceptionRecordRepository.findOne(id);
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == inceptionRecord.getBrotherhood().getId());

		final InceptionRecord saved = this.inceptionRecordRepository.save(inceptionRecord);

		return saved;
	}

	public void delete(final InceptionRecord inceptionRecord) {
		Assert.notNull(inceptionRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == inceptionRecord.getBrotherhood().getId());

		this.inceptionRecordRepository.delete(inceptionRecord);
	}

	//Reconstruct

	public InceptionRecord reconstruct(final InceptionRecord ir, final BindingResult binding) {
		InceptionRecord result;

		if (ir.getId() == 0)
			result = this.create();
		else
			result = this.inceptionRecordRepository.findOne(ir.getId());
		result.setTitle(ir.getTitle());
		result.setDescription(ir.getDescription());
		result.setPhotos(ir.getPhotos());
		this.validator.validate(result, binding);

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
}
