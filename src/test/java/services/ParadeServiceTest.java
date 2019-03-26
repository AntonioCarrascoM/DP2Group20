
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Parade;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ParadeServiceTest extends AbstractTest {

	// System under test: Parade ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ParadeService	paradeService;


	@Test
	public void ParadePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85
			{
				"brotherhood1", null, null, "createPositive", null
			}
			/*
			 * Positive test: A brotherhood creates a parade
			 * Requisite tested: Functional requirement - 3.3 An actor who is authenticated as a brotherhood must be
			 * able to manage their parades which includes creating them.
			 * Data coverage : A brotherhood creates a parade with 9 out of 9 valid attributes.
			 * Exception expected: None. A Brotherhood can create a parade.
			 */
			, {
				"brotherhood1", null, "parade2", "copyPositive", null
			}
		/*
		 * Positive: A Brotherhood copies a parade
		 * Requisite tested: Functional requirement - 3.2 An actor who is authenticated as a brotherhood must be able to
		 * make a copy of one of their parades
		 * Data coverage : A brotherhood copies a parade with 8 out of 9 valid attributes (but ticker).
		 * Exception expected: None. A Brotherhood can copy a parade.
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
	public void ParadeNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"brotherhood1", null, "parade1", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A Brotherhood tries to edit a parade that it is on final mode.
			 * Requisite tested: Functional requirement -3.3 An actor who is authenticated as a brotherhood must be
			 * able to manage their parades which includes updating them.
			 * Data coverage : A brotherhood tries to edit 1 out of 9 attributes (maxRow).
			 * Exception expected: IllegalArgumentException. A Brotherhood can not update a parade that it is on final mode.
			 */
			{
				"brotherhood2", null, "parade2", "editNegative1", IllegalArgumentException.class
			}
		/*
		 * Negative: A Brotherhood tries to edit a parade of another Brotherhood.
		 * Requisite tested: Functional requirement -3.3 An actor who is authenticated as a brotherhood must be
		 * able to manage their parades which includes updating them.
		 * Data coverage : A brotherhood tries to edit 1 out of 9 parade attributes of another actor (maxRow).
		 * Exception expected: IllegalArgumentException. A Brotherhood cannot update an another brotherhood´s parade.
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
		Class<?>

		caught;

		caught = null;
		try {
			super.authenticate(username);
			if (operation.equals("createPositive")) {
				final Parade parade = this.paradeService.create();
				parade.setFinalMode(false);
				parade.setMaxColumn(4);
				parade.setMaxRow(3);
				parade.setDescription("This is the test description");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date moment = sdf.parse("21/12/2020 12:34");
				parade.setMoment(moment);
				parade.setTitle("This is a positive test");
				this.paradeService.save(parade, false);

			} else if (operation.equals("copyPositive")) {
				final Parade parade = this.paradeService.findOne(this.getEntityId(id));
				this.paradeService.copy(parade);
			} else if (operation.equals("editNegative")) {
				final Parade parade = this.paradeService.findOne(this.getEntityId(id));
				parade.setMaxRow(5);
				this.paradeService.save(parade, false);
			} else if (operation.equals("editNegative1")) {
				final Parade parade = this.paradeService.findOne(this.getEntityId(id));
				parade.setMaxRow(5);
				this.paradeService.save(parade, false);
			}
			this.paradeService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
