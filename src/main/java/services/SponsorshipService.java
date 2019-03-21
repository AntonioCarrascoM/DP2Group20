
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SponsorshipRepository;
import domain.Configuration;
import domain.CreditCard;
import domain.Parade;
import domain.ParadeStatus;
import domain.Sponsor;
import domain.Sponsorship;

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

	@Autowired
	private ConfigurationService	configurationService;


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

		//Assertion is a valid make
		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		Assert.isTrue(makes.contains(ss.getCreditCard().getMake()));

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

		Assert.isTrue(!this.sponsorshipsWithExpiredCreditCards().contains(ss));

		ss.setIsActive(true);

		this.sponsorshipRepository.save(ss);
	}

	//Save the acumulated charge calculated when a sponsorship is displayed in a parade
	public Sponsorship saveFromParade(final Sponsorship s) {
		Assert.notNull(s);
		final Configuration config = this.configurationService.findAll().iterator().next();
		final Double vat = config.getVat();
		final Double fare = config.getFare();

		final Double charge = s.getCharge();
		final Double plusCharge = vat * fare;

		final Double total = charge + (plusCharge);
		s.setCharge(total);

		final Sponsorship saved = this.sponsorshipRepository.save(s);
		return saved;
	}

	public void pay(final Sponsorship ss) {
		Assert.notNull(ss);
		//Assertion that the user activating this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == ss.getSponsor().getId());

		ss.setCharge(0.0);

		this.sponsorshipRepository.save(ss);
	}

	//disableSponsorshipsWithExpiredCreditCards
	public void disableSponsorshipsWithExpiredCreditCards() {
		Collection<Sponsorship> ss = new ArrayList<>();
		ss = this.sponsorshipsWithExpiredCreditCards();
		for (final Sponsorship s : ss)
			s.setIsActive(false);
	}

	public Sponsorship reconstruct(final Sponsorship sponsorship, final BindingResult binding) {

		Sponsorship result;
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);
		if (sponsorship.getId() == 0)
			result = this.create();
		else
			result = this.sponsorshipRepository.findOne(sponsorship.getId());
		result.setBanner(sponsorship.getBanner());
		result.setTargetURL(sponsorship.getTargetURL());
		result.setCreditCard(sponsorship.getCreditCard());
		result.setParade(sponsorship.getParade());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this sponsorship has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSponsor().getId());

		//Assertion to make sure that the credit card has a valid expiration date.
		if (result.getCreditCard() != null) {
			Assert.isTrue(result.getCreditCard().getExpYear() >= year);

			if (result.getCreditCard().getExpYear() == year)
				Assert.isTrue(result.getCreditCard().getExpMonth() >= month);
		}

		//Assertion is a valid make
		final Collection<String> makes = this.configurationService.findAll().iterator().next().getCreditCardList();
		Assert.isTrue(makes.contains(result.getCreditCard().getMake()));

		//Assertion that the parade is accepted
		Assert.isTrue(result.getParade().getParadeStatus().equals(ParadeStatus.ACCEPTED));

		return result;

	}

	//Other methods

	//Returns the sponsorships of a certain parade
	public Collection<Sponsorship> getSponsorshipsByParade(final int id) {
		return this.sponsorshipRepository.getActiveSponsorshipsByParade(id);
	}

	//Ratio of active sponsorships
	public Double ratioOfActiveSponsorships() {
		return this.sponsorshipRepository.ratioOfActiveSponsorships();
	}

	//Sponsorships for a certain sponsor
	public Collection<Sponsorship> sponsorshipsFromSponsor(final int sponsorId) {
		return this.sponsorshipRepository.sponsorshipsFromSponsor(sponsorId);
	}

	//Sponsorships with expire credit cards
	public Collection<Sponsorship> sponsorshipsWithExpiredCreditCards() {
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final int month = Calendar.getInstance().get(Calendar.MONTH);
		return this.sponsorshipRepository.sponsorshipsWithExpiredCreditCards(year, month);
	}

}
