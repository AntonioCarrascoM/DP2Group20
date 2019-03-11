
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import domain.Actor;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private ActorService			actorService;


	//Supporting services --------------------------------

	//Simple CRUD methods

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final int id) {
		Assert.notNull(id);

		return this.configurationRepository.findOne(id);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		final Actor a = this.actorService.findByPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(auth));

		final Configuration saved = this.configurationRepository.save(configuration);
		//		this.actorService.checkSpam(saved.getSystemName());
		//		this.actorService.checkSpam(saved.getBanner());
		//		this.actorService.checkSpam(saved.getWelcomeEN());
		//		this.actorService.checkSpam(saved.getWelcomeES());
		return saved;
	}
}
