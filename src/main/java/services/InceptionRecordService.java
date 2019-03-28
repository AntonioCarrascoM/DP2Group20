
package services;

import java.util.Collection;

import javax.validation.ValidationException;

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
	private BrotherhoodService			brotherhoodService;

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
		if (inceptionRecord.getId() == 0)
			Assert.isTrue(this.inceptionRecordfromBrotherhood(inceptionRecord.getBrotherhood().getId()) == null);

		//Assertion to make sure that the inception record pictures are URLs
		if (inceptionRecord.getPhotos() != null && !inceptionRecord.getPhotos().isEmpty())
			Assert.isTrue(this.brotherhoodService.checkPictures(inceptionRecord.getPhotos()));

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

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());
		if (result.getId() == 0)
			Assert.isTrue(this.inceptionRecordfromBrotherhood(result.getBrotherhood().getId()) == null);
		//Assertion to make sure that the inception record pictures are URLs
		if (result.getPhotos() != null && !result.getPhotos().isEmpty())
			Assert.isTrue(this.brotherhoodService.checkPictures(result.getPhotos()));

		return result;

	}

	//Retrieves the tutorial of a certain section.
	public InceptionRecord inceptionRecordfromBrotherhood(final int brotherhoodId) {
		return this.inceptionRecordRepository.inceptionRecordfromBrotherhood(brotherhoodId);
	}

	public void flush() {
		this.inceptionRecordRepository.flush();
	}
}
