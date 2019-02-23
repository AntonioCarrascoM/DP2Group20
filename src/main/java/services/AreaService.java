
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import security.Authority;
import domain.Area;
import domain.Brotherhood;

@Service
@Transactional
public class AreaService {

	//Managed repository

	@Autowired
	private AreaRepository	areaRepository;

	@Autowired
	private ActorService	actorService;


	//Supporting services --------------------------------

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

		final Area saved = this.areaRepository.save(area);
		//		this.actorService.checkSpam(saved.getSystemName());
		//		this.actorService.checkSpam(saved.getBanner());
		//		this.actorService.checkSpam(saved.getWelcomeEN());
		//		this.actorService.checkSpam(saved.getWelcomeES());
		return saved;
	}
	public void delete(final Area area) {
		Assert.notNull(area);

		final Authority authAdmin = new Authority();
		authAdmin.setAuthority(Authority.ADMIN);
		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));

		this.areaRepository.delete(area);
	}

	//Other methods

	//The ratio and the count of brotherhoods per area
	public Collection<Double> ratioAndCountOfBrotherhoodsByArea(final int id) {
		return this.areaRepository.ratioAndCountOfBrotherhoodsByArea(id);
	}

	//The minimum, the maximum, the average, and the
	//standard deviation of the number of brotherhoods per area
	public Double[] minMaxAvgAndStddevOfBrotherhoodsByArea() {
		return this.areaRepository.minMaxAvgAndStddevOfBrotherhoodsByArea();
	}

}
