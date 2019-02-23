
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	//Supporting services --------------------------------

	//	@Autowired
	//	private ConfigurationService	configurationService;

	//Simple CRUD Methods --------------------------------

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		Assert.notNull(id);

		return this.actorRepository.findOne(id);
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);

		Assert.isTrue(this.findByPrincipal().getId() == actor.getId());

		this.actorRepository.delete(actor);
	}

	// Other business methods ----------------------

	public Actor findByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor a = this.actorRepository.findByUserAccountId(userAccount.getId());
		return a;
	}

	public void hashPassword(final Actor a) {
		final String oldPs = a.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = a.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.userAccountRepository.save(old);
		a.setUserAccount(newOne);
	}
	//TODO hay que hacer el checkSpam en base a lo nuevo. Debería simplemente dar un true o false y marcar el mensaje como spam. Luego en base a los marcados como spam calcular si es spammer o no. (Este checkSpam deberia ser mucho más simple)
	//	public boolean checkSpam(final String s) {
	//		final Configuration c = this.configurationService.findAll().iterator().next();
	//		boolean isSpam = false;
	//		if (s == null || StringUtils.isWhitespace(s))
	//			return isSpam;
	//		else {
	//			for (final String e : c.getSpamWords())
	//				if (s.contains(e)) {
	//					isSpam = true;
	//					final Actor a = this.findByPrincipal();
	//					a.setSuspicious(true);
	//					this.actorRepository.save(a);
	//
	//				}
	//			return isSpam;
	//		}
	//	}

	//TODO Revisar que el patrón es el mismo
	//Method to check the users email adjusts to the given patterns.
	public boolean checkUserEmail(final String email) {
		String parts[] = null;
		String aliasParts[];
		String mailParts[];
		String domainParts[];
		int i = 0;
		Boolean result = true;

		if (!email.contains("@"))
			result = false;

		//Checking the "alias <identifier@domain>" 
		else if (email.contains("<") && email.contains(">")) {
			parts = email.split("\\<");
			final String alias = parts[0];
			aliasParts = alias.split("\\s+");
			//Creating a list to know a size to end the for.
			final Collection<String> aliasList = new ArrayList<String>();
			for (final String s : aliasParts)
				aliasList.add(s);

			//Checking that aliasParts are alpha-numeric
			for (i = 0; i < aliasList.size(); i++)
				if (!aliasParts[i].matches("[A-Za-z0-9]+"))
					result = false;
			final String mail = parts[1].substring(0, parts[1].length() - 1);
			mailParts = mail.split("\\@");
			final String identifier = mailParts[0];
			final String domain = mailParts[1];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
			else {
				domainParts = domain.split("\\.");
				//Creating a list to know a size to end the for.
				final Collection<String> domainList = new ArrayList<String>();
				for (final String s : domainParts)
					domainList.add(s);
				for (i = 0; i < domainList.size(); i++)
					if (!domainParts[i].matches("[A-Za-z0-9]+"))
						result = false;
			}
		} else {
			//Checking the "identifier@domain" 
			mailParts = email.split("\\@");
			final String identifier = mailParts[0];
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
			else {
				final String domain = mailParts[1];
				domainParts = domain.split("\\.");
				if (domainParts.length <= 1)
					result = false;
				else {
					//Creating a list to know a size to end the for.
					final Collection<String> domainList = new ArrayList<String>();
					for (final String s : domainParts)
						domainList.add(s);
					for (i = 0; i < domainList.size(); i++)
						if (!domainParts[i].matches("[A-Za-z0-9]+"))
							result = false;
				}
			}
		}
		return result;
	}
	//Method to check the Admin email adjusts to the given patterns.
	public boolean checkAdminEmail(final String email) {
		String parts[] = null;
		String aliasParts[];
		String mailParts[];
		int i = 0;
		Boolean result = true;

		if (!email.contains("@"))
			result = false;

		//Checking the "alias <identifier@>" 
		else if (email.contains("<") && email.contains(">")) {
			parts = email.split("\\<");
			final String alias = parts[0];
			aliasParts = alias.split("\\s+");
			//Checking that aliasParts are alpha-numeric
			//Creating a list to know a size to end the for.
			//Creating a list to know a size to end the for.
			final Collection<String> aliasList = new ArrayList<String>();
			for (final String s : aliasParts)
				aliasList.add(s);

			//Checking that aliasParts are alpha-numeric
			for (i = 0; i < aliasList.size(); i++)
				if (!aliasParts[i].matches("[A-Za-z0-9]+"))
					result = false;

			final String mail = parts[1].substring(0, parts[1].length() - 1);
			mailParts = mail.split("\\@");
			final String identifier = mailParts[0];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
		} else {
			//Checking the "identifier@" 
			mailParts = email.split("\\@");
			final String identifier = mailParts[0];
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
		}
		return result;
	}

	//TODO Revisar el caso que pasaba con los quolets y customer. Crear un actor sin address y luego crear un objeto con ese actor y que al llegar al checkAddress no pete.
	//Method to check the address is correct. Either null or has content.
	public boolean checkAddress(String address) {
		if (address == "")
			address = null;
		return !StringUtils.isWhitespace(address);
	}
	//Method to check the phone only contains numbers
	public boolean checkPhone(final String phone) {
		Boolean result = true;
		String parts[] = null;
		String parts2[] = null;
		String parts3[] = null;
		String numero;

		if (phone.startsWith("+") && phone.contains("(") && phone.contains(")")) {
			numero = phone.substring(1);

			parts = numero.split("\\(");
			parts2 = parts[1].split("\\)");

			final String partesFinal = parts[0] + parts2[0] + parts2[1];
			if (parts2[0].length() > 3 || parts2[0].length() < 1)
				result = false;
			if (parts[0].length() > 3 || parts[0].length() < 1)
				result = false;
			if (!StringUtils.isNumericSpace(partesFinal))
				result = false;
		}

		if (phone.startsWith("+") && !phone.contains("(") && !phone.contains(")")) {
			numero = phone.substring(1);
			parts3 = numero.split("\\s+");
			if (parts3.length <= 1)
				result = false;
			if (!StringUtils.isNumericSpace(numero))
				result = false;
		}
		if (!phone.startsWith("+") && !StringUtils.isNumericSpace(phone))
			result = false;

		return result;
	}
	//Method to ban or unban an actor
	public void BanOrUnban(final int actorId) {
		final Actor a = this.actorRepository.findOne(actorId);

		//Checking that the selected actor is suspicious.
		Assert.isTrue(a.isSpammer());

		final Boolean ban = a.getUserAccount().getBanned();
		if (ban == true)
			a.getUserAccount().setBanned(false);
		if (ban == false)
			a.getUserAccount().setBanned(true);
	}

	//Other methods

	//TODO Arreglar el compute score para el nuevo caso
	//	public void computeScoreForAll() {
	//		final Collection<Customer> customers = this.customerService.getCustomersWithAtLeastOneEndorsement();
	//		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.getHandyWorkersWithAtLeastOneEndorsement();
	//
	//		//Checking that both collections aren't empty.
	//		if (!customers.isEmpty()) {
	//
	//			final Collection<Actor> actors = new ArrayList<Actor>();
	//
	//			actors.addAll(customers);
	//
	//			for (final Actor a : actors)
	//				this.computeScore(a);
	//		}
	//		if (!handyWorkers.isEmpty()) {
	//
	//			final Collection<Actor> actors = new ArrayList<Actor>();
	//
	//			actors.addAll(handyWorkers);
	//
	//			for (final Actor a : actors)
	//				this.computeScore(a);
	//		}
	//	}
	//	public Double computeScore(final Actor a) {
	//		Double count = 0.;
	//		Double score = 0.;
	//		Customer c = null;
	//		HandyWorker hw = null;
	//		final Authority authCust = new Authority();
	//		final Authority authHw = new Authority();
	//		final Authority authAdmin = new Authority();
	//		authCust.setAuthority(Authority.CUSTOMER);
	//		authHw.setAuthority(Authority.HANDYWORKER);
	//		authAdmin.setAuthority(Authority.ADMIN);
	//
	//		//Assertion the user calling this method has the correct privilege
	//		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authAdmin));
	//
	//		Collection<Enrolment> receivedEndorsements = new ArrayList<Enrolment>();
	//
	//		if (a.getUserAccount().getAuthorities().contains(authCust)) {
	//			c = (Customer) a;
	//			receivedEndorsements = this.receivedEndorsementsFromCustomer(a.getId());
	//
	//		} else if (a.getUserAccount().getAuthorities().contains(authHw)) {
	//			hw = (HandyWorker) a;
	//			receivedEndorsements = this.receivedEndorsementsFromHandyWorker(hw.getId());
	//
	//		}
	//		if (receivedEndorsements == null || receivedEndorsements.isEmpty())
	//			score = 0.;
	//		else {
	//			for (final Enrolment e : receivedEndorsements) {
	//
	//				final String comment = e.getComments();
	//				count = count + this.createScore(comment);
	//			}
	//			score = count / receivedEndorsements.size();
	//		}
	//
	//		if (a.getUserAccount().getAuthorities().contains(authCust)) {
	//			c.setScore(score);
	//			this.customerService.saveFromAdmin(c);
	//
	//		} else if (a.getUserAccount().getAuthorities().contains(authHw)) {
	//			hw.setScore(score);
	//			this.handyWorkerService.saveFromAdmin(hw);
	//		}
	//
	//		return score;
	//	}
	//	public Double createScore(final String s) {
	//		int countPositive = 0;
	//		int countNegative = 0;
	//		Double score = 0.;
	//		final Collection<String> positiveWords = this.configurationService.findAll().iterator().next().getPositiveWords();
	//		final Collection<String> negativeWords = this.configurationService.findAll().iterator().next().getNegativeWords();
	//
	//		for (final String x : positiveWords)
	//			if (s.contains(x))
	//				countPositive += 1;
	//		for (final String x : negativeWords)
	//			if (s.contains(x))
	//				countNegative += 1;
	//
	//		if (countPositive == 0 && countNegative == 0) {
	//			score = 0.;
	//			return score;
	//		} else {
	//			score = (countPositive - countNegative) * 1.0 / (countPositive + countNegative) * 1.0;
	//			return score;
	//		}
	//	}

	//Ancillary methods
	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public Actor getActorByUsername(final String username) {
		return this.actorRepository.getActorByUsername(username);
	}
}
