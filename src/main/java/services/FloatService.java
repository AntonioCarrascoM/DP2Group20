
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FloatRepository;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;

@Service
@Transactional
public class FloatService {

	//Managed repository

	@Autowired
	private FloatRepository		floatRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ProcessionService	processionService;

	@Autowired
	private Validator			validator;


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

		//Assertion to make sure that the pictures are URLs
		if (f.getPictures() != null && !f.getPictures().isEmpty())
			this.brotherhoodService.checkPictures(f.getPictures());

		final Float saved = this.floatRepository.save(f);
		return saved;
	}
	public void delete(final Float f) {
		Assert.notNull(f);
		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getBrotherhood().getId());

		final Collection<Procession> processions = f.getProcessions();
		if (!(processions.isEmpty()))
			for (final Procession p : processions) {
				final Collection<Float> processionFloats = p.getFloats();
				processionFloats.remove(f);
				p.setFloats(processionFloats);
				this.processionService.saveAux(p);

			}
		else
			f.getBrotherhood().setFloats(f.getBrotherhood().getFloats());
		this.brotherhoodService.save(f.getBrotherhood());

		this.floatRepository.delete(f);
	}

	//Reconstruct

	public Float reconstruct(final Float f, final BindingResult binding) {
		Float result;

		if (f.getId() == 0)
			result = this.create();
		else
			result = this.floatRepository.findOne(f.getId());
		result.setTitle(f.getTitle());
		result.setDescription(f.getDescription());
		result.setPictures(f.getPictures());

		this.validator.validate(result, binding);

		return result;

	}
}
