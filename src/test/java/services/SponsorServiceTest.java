
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Sponsor;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// System under test: Sponsor ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void SponsorPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"sponsor1", null, "sponsor1", "edit", null
			}
		/*
		 * Positive test: A sponsor edit his data.
		 * Requisite tested: Functional requirement - An actor who is autenticated must be able to edit his personal data.
		 * Data coverage : From 7 editable atributes we tried to edit 1 atribute (name) with valid data.
		 * Exception expected: None. A sponsor can edit his data.
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
	public void SponsorNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93
			{
				"sponsor1", null, "sponsor2", "edit2", IllegalArgumentException.class
			},
			/*
			 * Negative test: User sponsor1 tries to edit personal data of user sponsor2.
			 * Requisite tested: Functional requirement - An actor who is autenticated must be able to edit his personal data.
			 * Data coverage: From 7 editable atributes we tried to edit 1 atribute (name) with another user.
			 * IllegalArgumentException: Exception expected
			 * Exception expected: IllegalArgumentException A sponsor cannot edit others personal data.
			 */
			{
				null, " ", null, "create", ConstraintViolationException.class
			}
		/*
		 * Negative test: Registering a sponsor with invalid username.
		 * Requisite tested: Functional requirement - 15 An actor who is not autenticated must be able to register as Sponsor
		 * Data coverage: From 9 editable atributes we tried to create a sponsor with 1 atribute (username) with invalid data.
		 * Exception expected: ConstraintViolationException. Username cannot be blank.
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
	protected void template(final String username, final String st, final String sponsorId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (operation.equals("create")) {
				final Sponsor sponsor = this.sponsorService.create();

				sponsor.setName("nombre");
				sponsor.setMiddleName("mname");
				sponsor.setSurname("sname");
				sponsor.setAddress("calle");
				sponsor.setPhoto("");
				sponsor.setPhone("666666666");
				sponsor.getUserAccount().setPassword("test");
				sponsor.getUserAccount().setUsername(st);
				sponsor.setEmail("email@email.com");
				this.sponsorService.save(sponsor);
			}
			super.authenticate(username);
			if (operation.equals("edit")) {
				Sponsor sponsor;
				sponsor = this.sponsorService.findOne(this.getEntityId(sponsorId));

				sponsor.setName("Test");
				this.sponsorService.save(sponsor);
			} else if (operation.equals("edit2")) {
				Sponsor sponsor;
				sponsor = this.sponsorService.findOne(this.getEntityId(sponsorId));

				sponsor.setName("Test");
				this.sponsorService.save(sponsor);
			}
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
