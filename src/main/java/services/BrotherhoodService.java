
package services;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.UserAccount;
import domain.Box;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Float;
import domain.Parade;
import domain.SocialProfile;
import forms.FormObjectBrotherhood;

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

	@Autowired
	private Validator				validator;


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

		brotherhood.setParades(new ArrayList<Parade>());
		brotherhood.setEnrolments(new ArrayList<Enrolment>());
		brotherhood.setFloats(new ArrayList<Float>());

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

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(brotherhood.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(brotherhood.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(brotherhood.getPhone()));

		//Checking if the actor is bannable according to the "bannableActors" query.
		if (this.actorService.isBannable(brotherhood) == true)
			brotherhood.setSpammer(true);

		//Assertion to make sure that the pictures are URLs
		if (brotherhood.getPictures() != null && !brotherhood.getPictures().isEmpty())
			Assert.isTrue(this.checkPictures(brotherhood.getPictures()));

		if (brotherhood.getId() != 0) {
			Assert.isTrue(this.actorService.findByPrincipal().getId() == brotherhood.getId());
			saved2 = this.brotherhoodRepository.save(brotherhood);
		} else {
			final Brotherhood saved = this.brotherhoodRepository.save(brotherhood);
			this.actorService.hashPassword(saved);
			saved.setBoxes(this.boxService.generateDefaultFolders(saved));
			saved2 = this.brotherhoodRepository.save(saved);
		}

		return saved2;
	}

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);

		//Assertion that the user deleting this brotherhood has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == brotherhood.getId());

		this.brotherhoodRepository.delete(brotherhood);
	}

	//Reconstruct

	public Brotherhood reconstruct(final FormObjectBrotherhood fob, final BindingResult binding) {
		final Brotherhood result = this.create();

		Assert.isTrue(fob.getAcceptedTerms());
		Assert.isTrue(fob.getPassword().equals(fob.getSecondPassword()));

		result.setName(fob.getName());
		result.setMiddleName(fob.getMiddleName());
		result.setSurname(fob.getSurname());
		result.setPhoto(fob.getPhoto());
		result.setEmail(fob.getEmail());
		result.setPhone(fob.getPhone());
		result.setAddress(fob.getAddress());
		result.setTitle(fob.getTitle());
		result.setEstablishmentDate(fob.getEstablishmentDate());
		result.setPictures(fob.getPictures());
		result.getUserAccount().setUsername(fob.getUsername());
		result.getUserAccount().setPassword(fob.getPassword());

		this.validator.validate(result, binding);

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		return result;

	}

	public Brotherhood reconstructPruned(final Brotherhood brotherhood, final BindingResult binding) {
		Brotherhood result;

		result = this.brotherhoodRepository.findOne(brotherhood.getId());

		result.setName(brotherhood.getName());
		result.setMiddleName(brotherhood.getMiddleName());
		result.setSurname(brotherhood.getSurname());
		result.setPhoto(brotherhood.getPhoto());
		result.setEmail(brotherhood.getEmail());
		result.setPhone(brotherhood.getPhone());
		result.setAddress(brotherhood.getAddress());
		result.setTitle(brotherhood.getTitle());
		result.setEstablishmentDate(brotherhood.getEstablishmentDate());
		result.setPictures(brotherhood.getPictures());
		if (result.getArea() == null)
			result.setArea(brotherhood.getArea());
		this.validator.validate(result, binding);

		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getId());

		//Assertion that the email is valid according to the checkAdminEmail method.
		Assert.isTrue(this.actorService.checkUserEmail(result.getEmail()));

		//Assertion to check that the address isn't just a white space.
		Assert.isTrue(this.actorService.checkAddress(result.getAddress()));

		//Assertion that the phone is valid according to the checkPhone method.
		Assert.isTrue(this.actorService.checkPhone(result.getPhone()));

		//Assertion to make sure that the pictures are URLs
		if (result.getPictures() != null && !result.getPictures().isEmpty())
			Assert.isTrue(this.checkPictures(result.getPictures()));

		return result;

	}
	//CheckPictures method
	public boolean checkPictures(final String pictures) {
		boolean result = true;
		if (pictures != null)
			if (!pictures.isEmpty()) {
				final String[] splited = pictures.split(";");
				for (final String s : splited)
					if (!this.isURL(s))
						result = false;
			}
		return result;
	}
	public boolean isURL(final String url) {
		try {
			new URL(url);
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	//Other methods

	//Returns the enrolmentable brotherhoods given a member id
	public Collection<Brotherhood> nonEnrolmentableBrotherhoods(final int memberId) {
		return this.brotherhoodRepository.nonEnrolmentableBrotherhoods(memberId);
	}

	//Returns the enrolmentable members given a brotherhood id
	public Collection<Brotherhood> nonEnrolmentableMembers(final int brotherhoodId) {
		return this.brotherhoodRepository.nonEnrolmentableMembers(brotherhoodId);
	}

	//Returns the brotherhoods given a member id
	public Collection<Brotherhood> enrolmentMemberBrotherhoods(final int memberId) {
		return this.brotherhoodRepository.enrolmentMemberBrotherhoods(memberId);
	}

	//Returns the collection of spammer brotherhoods.
	public Collection<Brotherhood> spammerBrotherhoods() {
		return this.brotherhoodRepository.spammerBrotherhoods();
	}

	//The largest brotherhoods
	public Collection<Brotherhood> largestBrotherhoods() {
		final ArrayList<Brotherhood> brotherhoods = (ArrayList<Brotherhood>) this.brotherhoodRepository.largestBrotherhoods();
		if (brotherhoods.size() >= 1) {
			final ArrayList<Brotherhood> top = new ArrayList<Brotherhood>(brotherhoods.subList(brotherhoods.size() - 1, brotherhoods.size()));
			return top;
		} else
			return brotherhoods;
	}

	//The smallest brotherhoods
	public Collection<Brotherhood> smallestBrotherhoods() {
		final ArrayList<Brotherhood> brotherhoods = (ArrayList<Brotherhood>) this.brotherhoodRepository.smallestBrotherhoods();
		if (brotherhoods.size() >= 1) {
			final ArrayList<Brotherhood> top = new ArrayList<Brotherhood>(brotherhoods.subList(brotherhoods.size() - 1, brotherhoods.size()));
			return top;
		} else
			return brotherhoods;
	}
}
