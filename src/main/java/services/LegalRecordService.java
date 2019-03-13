
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
}
