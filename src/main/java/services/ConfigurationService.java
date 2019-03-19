
package services;

import java.util.ArrayList;
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

		//Checking that the configuration at least has the 3 basic priorities: LOW, NEUTRAL and HIGH
		this.checkPriorities(configuration);
		//Checking that the configuration at least has the 5 basic makes: VISA, MCARD, AMEX, DINNERS, FLY
		this.checkCreditCardList(configuration);

		final Configuration saved = this.configurationRepository.save(configuration);

		return saved;
	}

	private Collection<String> checkCreditCardList(final Configuration configuration) {

		if (configuration.getCreditCardList() == null)
			configuration.setCreditCardList(new ArrayList<String>());

		if (configuration.getCreditCardList().isEmpty()) {
			configuration.getCreditCardList().add("VISA");
			configuration.getCreditCardList().add("MCARD");
			configuration.getCreditCardList().add("AMEX");
			configuration.getCreditCardList().add("DINNERS");
			configuration.getCreditCardList().add("FLY");
		} else if (!configuration.getCreditCardList().contains("VISA") || !configuration.getCreditCardList().contains("MCARD") || !configuration.getCreditCardList().contains("AMEX") || !configuration.getCreditCardList().contains("DINNERS")
			|| !configuration.getCreditCardList().contains("FLY")) {

			if (!configuration.getCreditCardList().contains("VISA"))
				configuration.getCreditCardList().add("VISA");
			if (!configuration.getCreditCardList().contains("MCARD"))
				configuration.getCreditCardList().add("MCARD");
			if (!configuration.getCreditCardList().contains("AMEX"))
				configuration.getCreditCardList().add("AMEX");
			if (!configuration.getCreditCardList().contains("DINNERS"))
				configuration.getCreditCardList().add("DINNERS");
			if (!configuration.getCreditCardList().contains("FLY"))
				configuration.getCreditCardList().add("FLY");
		}
		return configuration.getCreditCardList();
	}

	private Collection<String> checkPriorities(final Configuration configuration) {

		if (configuration.getPriorityList() == null)
			configuration.setPriorityList(new ArrayList<String>());

		if (configuration.getPriorityList().isEmpty()) {
			configuration.getPriorityList().add("LOW");
			configuration.getPriorityList().add("NEUTRAL");
			configuration.getPriorityList().add("HIGH");
		} else if (!configuration.getPriorityList().contains("LOW") || !configuration.getPriorityList().contains("NEUTRAL") || !configuration.getPriorityList().contains("HIGH")) {
			if (!configuration.getPriorityList().contains("LOW"))
				configuration.getPriorityList().add("LOW");
			if (!configuration.getPriorityList().contains("NEUTRAL"))
				configuration.getPriorityList().add("NEUTRAL");
			if (!configuration.getPriorityList().contains("HIGH"))
				configuration.getPriorityList().add("HIGH");
		}
		return configuration.getPriorityList();
	}
}
