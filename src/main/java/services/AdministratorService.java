
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import domain.Box;
import domain.SocialProfile;

@Service
@Transactional
public class AdministratorService {

	//Managed repository ---------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;


	//Simple CRUD Methods --------------------------------

	public Administrator create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);

		final Administrator administrator = new Administrator();
		administrator.setSpammer(false);
		administrator.setSocialProfiles(new ArrayList<SocialProfile>());
		administrator.setUserAccount(account);
		administrator.setBoxes(new ArrayList<Box>());

		return administrator;
	}

	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final int id) {
		Assert.notNull(id);

		return this.administratorRepository.findOne(id);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		Administrator saved2;

		//TODO sobra un assert? este o el de checkAddress? Comprobar cuando esté en local todo ya.
		//Assertion to make sure the address is either null or written but not blank spaces.
		Assert.isTrue(!"\\s".equals(administrator.getAddress()) || administrator.getAddress() == null);

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(administrator.getEmail()));

		//TODO checkear que si creamos un actor SIN address y luego creamos un objeto con ese actor, no peta al llamar a este save.
		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(administrator.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(administrator.getPhone()));

		//Assertion that the user modifying this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());

		if (administrator.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());
			saved2 = this.administratorRepository.save(administrator);
		} else {
			final Administrator saved = this.administratorRepository.save(administrator);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.administratorRepository.save(saved);
		}
		//TODO Crear método checkSpam
		//		this.actorService.checkSpam(saved2.getUserAccount().getUsername());
		//		this.actorService.checkSpam(saved2.getName());
		//		this.actorService.checkSpam(saved2.getMiddleName());
		//		this.actorService.checkSpam(saved2.getSurname());
		//		this.actorService.checkSpam(saved2.getAddress());
		//		this.actorService.checkSpam(saved2.getPhoto());
		//		this.actorService.checkSpam(saved2.getEmail());

		return saved2;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);

		//Assertion that the user deleting this administrator has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == administrator.getId());

		this.administratorRepository.delete(administrator);
	}

	//Other methods

	//Returns the collection of suspicious administrators.
	public Collection<Administrator> spammerAdministrators() {
		return this.administratorRepository.spammerAdministrators();
	}
}
