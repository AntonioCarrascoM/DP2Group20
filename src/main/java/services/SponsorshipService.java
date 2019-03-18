
package services;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.CreditCard;
import domain.Parade;
import domain.ParadeStatus;
import domain.Sponsor;
import domain.Sponsorship;
import forms.FormObjectSponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	// Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	// Simple CRUD methods

	public Sponsorship create() {
		final Sponsorship ss = new Sponsorship();
		final Sponsor s = (Sponsor) this.actorService.findByPrincipal();
		ss.setSponsor(s);
		ss.setCreditCard(new CreditCard());
		ss.setParade(new Parade());
		ss.setIsActive(false);
		ss.setCharge(0.0);
		return ss;
	}

	public Sponsorship findOne(final int id) {
		Assert.notNull(id);

		return this.sponsorshipRepository.findOne(id);
	}

	public Collection<Sponsorship> findAll() {
		return this.sponsorshipRepository.findAll();
	}

	public Sponsorship save(final Sponsorship ss) {
		Assert.notNull(ss);

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (ss.getCreditCard() != null) {
			Assert.isTrue(ss.getCreditCard().getExpYear() >= year);

			if (ss.getCreditCard().getExpYear() == year)
				Assert.isTrue(ss.getCreditCard().getExpMonth() >= month);
		}

		//Assertion that the parade is accepted
		Assert.isTrue(ss.getParade().getParadeStatus().equals(ParadeStatus.ACCEPTED));

		final Sponsorship saved = this.sponsorshipRepository.save(ss);
		return saved;
	}

	public void delete(final Sponsorship ss) {
		Assert.notNull(ss);

		//Assertion that the user deleting this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());

		this.sponsorshipRepository.delete(ss);
	}

	public void remove(final Sponsorship ss) {
		Assert.notNull(ss);

		//Assertion that the user removing this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());
		//Assertion that the sponsorship is active
		Assert.isTrue(ss.getIsActive());

		ss.setIsActive(false);

		this.sponsorshipRepository.save(ss);
	}

	public void activate(final Sponsorship ss) {
		Assert.notNull(ss);

		//Assertion that the user activating this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());
		//Assertion that the sponsorship is deactive
		Assert.isTrue(ss.getIsActive() == false);

		ss.setIsActive(true);

		this.sponsorshipRepository.save(ss);
	}

	public void pay(final Sponsorship ss) {
		Assert.notNull(ss);
		//Assertion that the user activating this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());

		ss.setCharge(0.0);

		this.sponsorshipRepository.save(ss);
	}

	public Sponsorship reconstruct(final FormObjectSponsorship foss, final BindingResult binding) {
		final Sponsorship result = this.create();

		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);

		result.setBanner(foss.getBanner());
		result.setTargetURL(foss.getTargetURL());
		result.setCreditCard(foss.getCreditCard());
		result.setParade(foss.getParade());

		this.validator.validate(result, binding);

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			Assert.isTrue(result.getCreditCard().getExpYear() >= year);

			if (result.getCreditCard().getExpYear() == year)
				Assert.isTrue(result.getCreditCard().getExpMonth() >= month);
		}

		//Assertion that the parade is accepted
		Assert.isTrue(result.getParade().getParadeStatus().equals(ParadeStatus.ACCEPTED));

		return result;
	}

	public Sponsorship reconstructPruned(final Sponsorship sponsorship, final BindingResult binding) {

		Sponsorship result;
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);
		result = this.sponsorshipRepository.findOne(sponsorship.getId());

		result.setBanner(sponsorship.getBanner());
		result.setTargetURL(sponsorship.getTargetURL());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setParade(sponsorship.getParade());

		this.validator.validate(result, binding);

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			Assert.isTrue(result.getCreditCard().getExpYear() >= year);

			if (result.getCreditCard().getExpYear() == year)
				Assert.isTrue(result.getCreditCard().getExpMonth() >= month);
		}

		//Assertion that the parade is accepted
		Assert.isTrue(result.getParade().getParadeStatus().equals(ParadeStatus.ACCEPTED));

		return result;

	}

	//Other methods

  //Ratio of active sponsorships
	public Double ratioOfActiveSponsorships() {
		return this.sponsorshipRepository.ratioOfActiveSponsorships();
	}
  
  //Sponsorships for a certain sponsor
	public Collection<Sponsorship> sponsorshipsFromSponsor(final int sponsorId) {
		return this.sponsorshipRepository.sponsorshipsFromSponsor(sponsorId);
	}

}
