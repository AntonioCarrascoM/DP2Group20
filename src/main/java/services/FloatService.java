
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FloatRepository;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	//Managed repository

	@Autowired
	private FloatRepository	floatRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService	actorService;


	//Simple CRUD methods

	public Float create() {

		final Float f = new Float();

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		f.setBrotherhood(b);

		f.setProcessions(new ArrayList<Procession>());

		return f;
	}

	public Collection<Float> findAll() {
		return this.floatRepository.findAll();
	}

	public Float findOne(final int id) {
		Assert.notNull(id);

		return this.floatRepository.findOne(id);
	}

	public Float save(final Float f) {
		Assert.notNull(f);

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getBrotherhood().getId());

		final Float saved = this.floatRepository.save(f);
		//		this.actorService.checkSpam(saved.getSystemName());
		//		this.actorService.checkSpam(saved.getBanner());
		//		this.actorService.checkSpam(saved.getWelcomeEN());
		//		this.actorService.checkSpam(saved.getWelcomeES());
		return saved;
	}
	public void delete(final Float f) {
		Assert.notNull(f);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getBrotherhood().getId());

		this.floatRepository.delete(f);
	}
}
