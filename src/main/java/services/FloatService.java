
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
import domain.Parade;

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
	private ParadeService		paradeService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Float create() {

		final Float f = new Float();

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		f.setBrotherhood(b);

		f.setParades(new ArrayList<Parade>());

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
			Assert.isTrue(this.brotherhoodService.checkPictures(f.getPictures()));

		final Float saved = this.floatRepository.save(f);
		return saved;
	}
	public void delete(final Float f) {
		Assert.notNull(f);
		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == f.getBrotherhood().getId());

		final Collection<Parade> parades = f.getParades();
		if (!(parades.isEmpty()))
			for (final Parade p : parades) {
				final Collection<Float> paradeFloats = p.getFloats();
				paradeFloats.remove(f);
				p.setFloats(paradeFloats);
				this.paradeService.saveAux(p);

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

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		//Assertion to make sure that the pictures are URLs
		if (result.getPictures() != null && !result.getPictures().isEmpty())
			Assert.isTrue(this.brotherhoodService.checkPictures(result.getPictures()));

		return result;

	}
}
