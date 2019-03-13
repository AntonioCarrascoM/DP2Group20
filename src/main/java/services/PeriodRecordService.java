
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
}
