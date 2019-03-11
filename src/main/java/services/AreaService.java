
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AreaRepository;
import security.Authority;
import domain.Area;
import domain.Brotherhood;

@Service
@Transactional
public class AreaService {

	//Managed repository

	@Autowired
	private AreaRepository		areaRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Area create() {

		final Area area = new Area();
		area.setBrotherhoods(new ArrayList<Brotherhood>());

		return area;
	}

	public Collection<Area> findAll() {
		return this.areaRepository.findAll();
	}

	public Area findOne(final int id) {
		Assert.notNull(id);

		return this.areaRepository.findOne(id);
	}

	public Area save(final Area area) {
		Assert.notNull(area);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);
		//Assertion that the user modifying this area has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		//Assertion to make sure that the pictures are URLs
		if (area.getPictures() != null && !area.getPictures().isEmpty())
			Assert.isTrue(this.brotherhoodService.checkPictures(area.getPictures()));

		final Area saved = this.areaRepository.save(area);
		return saved;
	}
	public void delete(final Area area) {
		Assert.notNull(area);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);
		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		//Assertion to make sure that the area has no brotherhoods assigned.
		Assert.isTrue(area.getBrotherhoods().isEmpty());

		this.areaRepository.delete(area);
	}

	//Reconstruct

	public Area reconstruct(final Area area, final BindingResult binding) {
		Area result;
		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);

		if (area.getId() == 0)
			result = this.create();
		else
			result = this.findOne(area.getId());

		result.setName(area.getName());
		result.setPictures(area.getPictures());

		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		if (result.getPictures() != null && !result.getPictures().isEmpty())
			Assert.isTrue(this.brotherhoodService.checkPictures(result.getPictures()));

		this.validator.validate(result, binding);

		return result;

	}

	//Other methods

	//The ratio of brotherhoods per area
	public Collection<Double> ratioOfBrotherhoodsByArea() {
		return this.areaRepository.ratioOfBrotherhoodsByArea();
	}

	//The count of brotherhoods per area
	public Collection<Integer> countOfBrotherhoodsByArea() {
		return this.areaRepository.countOfBrotherhoodsByArea();
	}

	//The minimum, the maximum, the average, and the
	//standard deviation of the number of brotherhoods per area
	public Double[] minMaxAvgAndStddevOfBrotherhoodsByArea() {
		return this.areaRepository.minMaxAvgAndStddevOfBrotherhoodsByArea();
	}

}
