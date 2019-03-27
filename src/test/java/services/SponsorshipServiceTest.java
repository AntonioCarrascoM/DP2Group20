
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsorship;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// System under test: Sponsorship ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService		sponsorService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private ParadeService		paradeService;


	@Test
	public void SponsorshipPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89
			{
				"sponsor1", null, "sponsorship3", "edit", null
			}
			/*
			 * Positive test: A sponsor edits his sponsorship.
			 * Requisite tested: Functional requirement - 16.1 An actor who is autenticated as a sponsor must be
			 * able to edit his sponsorship.
			 * Data coverage : From 9 editable atributes we tried to edit 1 atribute (banner) with valid data.
			 * Exception expected: None. A Sponsor can edit his sponsorships.
			 */
			, {
				"sponsor1", null, "parade3", "create", null
			}
		/*
		 * Positive: A sponsor tries to create a sponsorship
		 * Requisite tested: Functional requirement - 16.1 An actor who is autenticated as a sponsor must be
		 * able to create sponsorhips.
		 * Data coverage : We created a sponsorship with an accepted parade (parade3) and a valid VISA credit card.
		 * Exception expected: None. A Sponsor can create sponsorships.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	@Test
	public void SponsorshipNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.1% | Covered Instructions 95 | Missed Instructions 6 | Total Instructions 101
			{
				"sponsor1", null, "sponsorship4", "edit2", IllegalArgumentException.class
			},
			/*
			 * Negative: A sponsor tries to edit a sponsorship that not owns.
			 * Requisite tested: Functional requirement - 16.1 An actor who is authenticated as a sponsor must be
			 * able to edit his sponsorship.
			 * Data coverage : From 9 editable attributes we tried to edit 1 attribute (banner).
			 * Exception expected: IllegalArgumentException. A Sponsor can not edit sponsorships from another sponsor.
			 */
			{
				"sponsor1", "test", "parade3", "create2", ConstraintViolationException.class
			}
		/*
		 * Negative: A sponsor tries to create an invalid sponsorship
		 * Requisite tested: Functional requirement - 16.1 An actor who is authenticated as a sponsor must be
		 * able to create sponsorships
		 * Data coverage : We tried to create a sponsorship providing 1 invalid out of 9 editable attributes
		 * Exception expected: ConstraintViolationException. The Sponsorship Banner must be a valid url.
		 */
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void template(final String username, final String st, final String id, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			if (operation.equals("edit")) {
				final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(id));
				sponsorship.setBanner("https://www.test.com");
				this.sponsorshipService.save(sponsorship);

			} else if (operation.equals("edit2")) {
				final Sponsorship sponsorship = this.sponsorshipService.findOne(this.getEntityId(id));
				sponsorship.setBanner("https://www.test.com");
				this.sponsorshipService.save(sponsorship);
			} else if (operation.equals("create")) {
				final Sponsorship s = this.sponsorshipService.create();
				final CreditCard c = new CreditCard();
				c.setHolder("test");
				c.setMake("VISA");
				c.setNumber("4167363478835187");
				c.setExpMonth(8);
				c.setExpYear(2020);
				c.setCvv(123);

				s.setBanner("http://www.test.com");
				s.setTargetURL("http://www.test.com");
				s.setCreditCard(c);
				s.setParade(this.paradeService.findOne(this.getEntityId(id)));

				this.sponsorshipService.save(s);
			} else if (operation.equals("create2")) {
				final Sponsorship s = this.sponsorshipService.create();
				final CreditCard c = new CreditCard();
				c.setHolder("test");
				c.setMake("VISA");
				c.setNumber("4167363478835187");
				c.setExpMonth(8);
				c.setExpYear(2020);
				c.setCvv(123);

				s.setBanner(st);
				s.setTargetURL("http://www.test.com");
				s.setCreditCard(c);
				s.setParade(this.paradeService.findOne(this.getEntityId(id)));

				this.sponsorshipService.save(s);
			}
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
