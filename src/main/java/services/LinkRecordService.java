
package services;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LinkRecordRepository;
import domain.Brotherhood;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	//Managed repository ---------------------------------

	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	//Simple CRUD Methods --------------------------------

	public LinkRecord create() {

		//Creating entity
		final LinkRecord ir = new LinkRecord();
		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		ir.setBrotherhood(b);
		return ir;
	}

	public Collection<LinkRecord> findAll() {
		return this.linkRecordRepository.findAll();
	}

	public LinkRecord findOne(final int id) {
		Assert.notNull(id);

		return this.linkRecordRepository.findOne(id);
	}

	public LinkRecord save(final LinkRecord linkRecord) {
		Assert.notNull(linkRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == linkRecord.getBrotherhood().getId());

		final LinkRecord saved = this.linkRecordRepository.save(linkRecord);

		return saved;
	}

	public void delete(final LinkRecord linkRecord) {
		Assert.notNull(linkRecord);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == linkRecord.getBrotherhood().getId());

		this.linkRecordRepository.delete(linkRecord);
	}

	//Reconstruct

	public LinkRecord reconstruct(final LinkRecord lir, final BindingResult binding) {
		LinkRecord result;

		if (lir.getId() == 0)
			result = this.create();
		else
			result = this.linkRecordRepository.findOne(lir.getId());
		result.setTitle(lir.getTitle());
		result.setDescription(lir.getDescription());
		result.setLink(lir.getLink());
		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
	//The period records from a  brotherhood
	public Collection<LinkRecord> linkRecordsfromBrotherhood(final int brotherhoodId) {
		return this.linkRecordRepository.linkRecordsfromBrotherhood(brotherhoodId);
	}
}
