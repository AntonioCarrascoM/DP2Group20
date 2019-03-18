
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.SocialProfile;
import domain.Sponsor;
import forms.FormObjectSponsor;

@Service
@Transactional
public class SponsorService {

	//Managed repository ---------------------------------

	@Autowired
	private SponsorRepository	sponsorRepository;

	//Supporting services --------------------------------

	@Autowired
	private BoxService			boxService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD Methods --------------------------------

	public Sponsor create() {
		final Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		final UserAccount account = new UserAccount();
		account.setAuthorities(Arrays.asList(a));
		account.setBanned(false);

		final Sponsor sponsor = new Sponsor();
		sponsor.setSpammer(false);
		sponsor.setSocialProfiles(new ArrayList<SocialProfile>());
		sponsor.setUserAccount(account);
		sponsor.setBoxes(new ArrayList<Box>());

		return sponsor;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int id) {
		Assert.notNull(id);

		return this.sponsorRepository.findOne(id);
	}

	public Sponsor save(final Sponsor Sponsor) {
		Assert.notNull(Sponsor);
		Sponsor saved2;

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(Sponsor.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(Sponsor.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(Sponsor.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(Sponsor) == true)
			Sponsor.setSpammer(true);

		if (Sponsor.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == Sponsor.getId());
			saved2 = this.sponsorRepository.save(Sponsor);
		} else {
			final Sponsor saved = this.sponsorRepository.save(Sponsor);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.sponsorRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Sponsor Sponsor) {
		Assert.notNull(Sponsor);

		//Assertion that the user deleting this Sponsor has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == Sponsor.getId());

		this.sponsorRepository.delete(Sponsor);
	}

	//Reconstruct

	public Sponsor reconstruct(final FormObjectSponsor fos, final BindingResult binding) {
		final Sponsor result = this.create();

		Assert.isTrue(fos.getAcceptedTerms());
		Assert.isTrue(fos.getPassword().equals(fos.getSecondPassword()));

		result.setName(fos.getName());
		result.setMiddleName(fos.getMiddleName());
		result.setSurname(fos.getSurname());
		result.setPhoto(fos.getPhoto());
		result.setEmail(fos.getEmail());
		result.setPhone(fos.getPhone());
		result.setAddress(fos.getAddress());
		result.getUserAccount().setUsername(fos.getUsername());
		result.getUserAccount().setPassword(fos.getPassword());

		this.validator.validate(result, binding);

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public Sponsor reconstructPruned(final Sponsor sponsor, final BindingResult binding) {
		Sponsor result;

		result = this.sponsorRepository.findOne(sponsor.getId());

		result.setName(sponsor.getName());
		result.setMiddleName(sponsor.getMiddleName());
		result.setSurname(sponsor.getSurname());
		result.setPhoto(sponsor.getPhoto());
		result.setEmail(sponsor.getEmail());
		result.setPhone(sponsor.getPhone());
		result.setAddress(sponsor.getAddress());

		this.validator.validate(result, binding);

		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	//Other methods

	//The average, the minimum, the maximum, and the standard deviation of active sponsorships per sponsor
	public Double[] avgMinMaxAndStddevOfActiveSponsorshipsPerSponsor() {
		return this.sponsorRepository.avgMinMaxAndStddevOfActiveSponsorshipsPerSponsor();
	}

	//The top-5 sponsors in terms of number of active sponsorships.
	public Collection<Sponsor> top5SponsorsByActiveSponsorships() {
		return this.sponsorRepository.top5SponsorsByActiveSponsorships();
	}

}
