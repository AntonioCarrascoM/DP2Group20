
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
}
