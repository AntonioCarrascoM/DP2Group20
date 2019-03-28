
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Brotherhood;
import domain.Parade;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BrotherhoodServiceTest extends AbstractTest {

	// System under test: Brotherhood ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private ParadeService		paradeService;


	@Test
	public void BrotherhoodPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.2% | Covered Instructions 62 | Missed Instructions 6 | Total Instructions 68
			{
				"brotherhood1", null, null, "editPositive", null
			}
		/*
		 * Positive test: A brotherhood edit his data.
		 * Requisite tested: Functional requirement - 9.2 An actor who is authenticated must be able to edit his personal data.
		 * Data coverage : From 12 editable attributes we tried to edit 2 attributes (name, middleName) with valid data.
		 * Exception expected: None. A brotherhood can edit his data.
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
	public void BrotherhoodNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"brotherhood1", null, "brotherhood2", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A brotherhood tries to edit the another brotherhood personal data.
			 * Requisite tested: Functional requirement - An actor who is authenticated must be able to edit his personal data.
			 * Data coverage: From 12 editable attributes we tried to edit 4 attributes (name, middleName, title, address) of another user.
			 * Exception expected: IllegalArgumentException A brotherhood cannot edit others personal data.
			 */

			{
				"brotherhood1", null, "parade3", "editNegative1", IllegalArgumentException.class
			},

		/*
		 * Negative test: A brotherhood tries to edit another brotherhood parade .
		 * Requisite tested: Functional requirement - An actor who is authenticated as a brotherhood must be able to manage
		 * their parades, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage: From 9 editable attributes we tried to edit 2 attributes (maxRow, maxColumn).
		 * Exception expected: IllegalArgumentException A brotherhood cannot edit others brotherhood parade.
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
			if (operation.equals("editPositive")) {
				final Brotherhood brotherhood = this.brotherhoodService.findOne(this.getEntityId(username));
				brotherhood.setName("Test name");
				brotherhood.setMiddleName("Test middlename");
				this.brotherhoodService.save(brotherhood);
			} else if (operation.equals("editNegative")) {
				final Brotherhood brotherhood = this.brotherhoodService.findOne(this.getEntityId(id));
				brotherhood.setName("Test negative name");
				brotherhood.setMiddleName("Test negative middle name");
				brotherhood.setTitle("Test title");
				brotherhood.setAddress("Test address");
				this.brotherhoodService.save(brotherhood);

			} else if (operation.equals("editNegative1")) {
				final Parade parade = this.paradeService.findOne(this.getEntityId(id));
				parade.setMaxColumn(2);
				parade.setMaxColumn(4);
				this.paradeService.save(parade, false);
			}
			this.brotherhoodService.flush();
			this.paradeService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
