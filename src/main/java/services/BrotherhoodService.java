
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Brotherhood;
import domain.SocialProfile;

@Service
@Transactional
public class BrotherhoodService {

	//Managed repository ---------------------------------

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;


	//Simple CRUD Methods --------------------------------

	public Brotherhood create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);

		final Brotherhood brotherhood = new Brotherhood();
		brotherhood.setSpammer(false);
		brotherhood.setSocialProfiles(new ArrayList<SocialProfile>());
		brotherhood.setUserAccount(account);
		brotherhood.setBoxes(new ArrayList<Box>());

		return brotherhood;
	}

	public Collection<Brotherhood> findAll() {
		return this.brotherhoodRepository.findAll();
	}

	public Brotherhood findOne(final int id) {
		Assert.notNull(id);

		return this.brotherhoodRepository.findOne(id);
	}

	public Brotherhood save(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		Brotherhood saved2;

		//TODO sobra un assert? este o el de checkAddress? Comprobar cuando esté en local todo ya.
		//Assertion to make sure the address is either null or written but not blank spaces.
		Assert.isTrue(!"\\s".equals(brotherhood.getAddress()) || brotherhood.getAddress() == null);

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkAdminEmail(brotherhood.getEmail()));

		//TODO checkear que si creamos un actor SIN address y luego creamos un objeto con ese actor, no peta al llamar a este save.
		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(brotherhood.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(brotherhood.getPhone()));

		//Assertion that the user modifying this brotherhood has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == brotherhood.getId());

		if (brotherhood.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == brotherhood.getId());
			saved2 = this.brotherhoodRepository.save(brotherhood);
		} else {
			final Brotherhood saved = this.brotherhoodRepository.save(brotherhood);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.brotherhoodRepository.save(saved);
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

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);

		//Assertion that the user deleting this brotherhood has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == brotherhood.getId());

		this.brotherhoodRepository.delete(brotherhood);
	}

	//Other methods

	//Returns the collection of spammer brotherhoods.
	public Collection<Brotherhood> spammerBrotherhoods() {
		return this.brotherhoodRepository.spammerBrotherhoods();
	}

	//The largest brotherhoods
	//TODO Revisar esto porque actualmente solo estamos devolviendo una y pueden ser 2 con el mismo numero de enrolments
	public Collection<Brotherhood> largestBrotherhoods() {
		final ArrayList<Brotherhood> brotherhoods = (ArrayList<Brotherhood>) this.brotherhoodRepository.largestBrotherhoods();
		if (brotherhoods.size() >= 1) {
			final ArrayList<Brotherhood> top = new ArrayList<Brotherhood>(brotherhoods.subList(brotherhoods.size() - 1, brotherhoods.size()));
			return top;
		} else
			return brotherhoods;
	}

	//The smallest brotherhoods
	//TODO Revisar esto porque actualmente solo estamos devolviendo una y pueden ser 2 con el mismo numero de enrolments
	public Collection<Brotherhood> smallestBrotherhoods() {
		final ArrayList<Brotherhood> brotherhoods = (ArrayList<Brotherhood>) this.brotherhoodRepository.largestBrotherhoods();
		if (brotherhoods.size() >= 1) {
			final ArrayList<Brotherhood> top = new ArrayList<Brotherhood>(brotherhoods.subList(brotherhoods.size() - 1, brotherhoods.size()));
			return top;
		} else
			return brotherhoods;
	}
}
