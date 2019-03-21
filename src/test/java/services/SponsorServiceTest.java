
package services;

import javax.transaction.Transactional;

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
	public void driver() {
		final Object testingData[][] = {
			{
				"sponsor1", null, "sponsor1", "edit", null
			},//Positive test: A sponsor edit his data
			{
				"sponsor1", null, "sponsor2", "edit2", IllegalArgumentException.class
			},//Negative test: Another sponsor tries to edit others data
			{
				null, "test", null, "create", AssertionError.class
			}
		//Negative test: Registering a sponsor with invalid make
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
				sponsor.getUserAccount().setPassword("1234");
				sponsor.getUserAccount().setUsername("sponsorTesting");
				sponsor.setEmail(st);

				sponsor.setName("Test");
				this.sponsorService.save(sponsor);
				System.out.println(sponsor.getName());
			}
			super.authenticate(username);
			if (operation.equals("edit")) {
				Sponsor sponsor;

				sponsor = this.sponsorService.findOne(this.getEntityId(sponsorId));

				sponsor.setName("Test");
				this.sponsorService.save(sponsor);
				System.out.println(sponsor.getName());
			} else if (operation.equals("edit2")) {
				Sponsor sponsor;

				sponsor = this.sponsorService.findOne(this.getEntityId(sponsorId));

				sponsor.setName("Test");
				this.sponsorService.save(sponsor);
				System.out.println(sponsor.getName());
			}
			this.sponsorService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
