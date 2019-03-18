
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RecordRepository;
import domain.Record;

@Service
@Transactional
public class RecordService {

	//Managed repository ---------------------------------

	@Autowired
	private RecordRepository	recordRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;


	//Simple CRUD Methods --------------------------------

	public Collection<Record> findAll() {
		return this.recordRepository.findAll();
	}

	public Record findOne(final int id) {
		Assert.notNull(id);

		return this.recordRepository.findOne(id);
	}

	public Record save(final Record record) {
		Assert.notNull(record);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == record.getBrotherhood().getId());

		final Record saved = this.recordRepository.save(record);

		return saved;
	}

	public void delete(final Record record) {
		Assert.notNull(record);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == record.getBrotherhood().getId());

		this.recordRepository.delete(record);
	}

	//The average, the minimum, the maximum, and the standard deviation of the
	//number of records per history
	public Double[] avgMinMaxStddevRecordsForHistory() {
		return this.recordRepository.avgMinMaxStddevRecordsForHistory();
	}
}
