
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Float;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FloatServiceTest extends AbstractTest {

	// System under test: Float ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private FloatService	floatService;


	@Test
	public void FloatPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.2% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85
			{
				"brotherhood1", null, null, "createPositive", null
			}
			/*
			 * Positive test: A brotherhood creates a float
			 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a brotherhood must be able to
			 * manage their floats, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : A brotherhood creates a float with 2 out of 3 attributes.
			 * Exception expected: None. A Brotherhood can create a float.
			 */
			, {
				"brotherhood1", null, "float1", "editPositive", null
			}
		/*
		 * Positive test: A Brotherhood edits a float
		 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a brotherhood must be able to
		 * manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : A brotherhood tries to edit 1 out of 3 valid attributes(title).
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
	public void FloatNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"member1", null, "float1", "editNegative", IllegalArgumentException.class
			},
			/*
			 * Negative test: A member tries to edit a float
			 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a brotherhood must be able to
			 * manage their floats, which includes listing, showing, creating, updating, and deleting them.
			 * Data coverage : A member tries to edit 1 out of 3 valid attributes (description).
			 * Exception expected: IllegalArgumentException. A member cannot edit a float.
			 */
			{
				"sponsor1", null, "float2", "deleteNegative", IllegalArgumentException.class
			}
		/*
		 * Negative test: A sponsor tries to delete a float
		 * Requisite tested: Functional requirement - 10.1 An actor who is authenticated as a brotherhood must be able to
		 * manage their floats, which includes listing, showing, creating, updating, and deleting them.
		 * Data coverage : A sponsor tries to delete a float.
		 * Exception expected: IllegalArgumentException. A sponsor cannot delete a float.
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
				final Float f = this.floatService.create();
				f.setTitle("This is the title test");
				f.setDescription("This is the description test");
				this.floatService.save(f);

			} else if (operation.equals("editPositive")) {
				final Float f = this.floatService.findOne(this.getEntityId(id));
				f.setTitle("This is the edit title");
				this.floatService.save(f);
			} else if (operation.equals("editNegative")) {
				final Float f = this.floatService.findOne(this.getEntityId(id));
				f.setTitle("This is the title test");
				f.setDescription("This is the description test");
				this.floatService.save(f);
			} else if (operation.equals("deleteNegative")) {
				final Float f = this.floatService.findOne(this.getEntityId(id));
				this.floatService.delete(f);
			}
			this.floatService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
