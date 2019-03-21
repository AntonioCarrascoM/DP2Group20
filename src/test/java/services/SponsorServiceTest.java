
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

	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void SponsorPositiveTest() {
		final Object testingData[][] = {
			{
				"sponsor1", null, "sponsor1", "edit", null
			}
		//Positive test: A sponsor edit his data. 
		//Exception expected: None. A sponsor can edit his data.
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
			{
				"sponsor1", null, "sponsor2", "edit2", IllegalArgumentException.class
			},
			//Negative test: Another sponsor1 tries to edit personal data of sponsor2. 
			//Exception expected: IllegalArgumentException A sponsor cannot edit others personal data.
			{
				null, " ", null, "create", ConstraintViolationException.class
			}
		//Negative test: Registering a sponsor with invalid username. 
		//Exception expected: ConstraintViolationException. Username cannot be blank.
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
