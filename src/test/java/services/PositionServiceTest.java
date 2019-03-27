
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Position;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PositionServiceTest extends AbstractTest {

	// System under test: Position ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PositionService	positionService;


	@Test
	public void PositionPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85
			{
				"admin", null, null, "createPositive", null
			}
			/*
			 * Positive: An administrator creates a position.
			 * Requisite tested: Functional requirement - 10.2. An actor who is authenticated as an administrator must be able to manage
			 * the catalogue of positions, which includes listing, showing, creating, updating, and deleting them.
			 * Positions can be deleted as long as they are not used.
			 * Data coverage : We tried to create a position with 2 out of 2 valid editable attributes
			 * Exception expected: None. An Admin can create a position
			 */
			, {
				"admin", null, "position4", "deletePositive", null
			}
		/*
		 * Positive: An administrator delete a position.
		 * Requisite tested: Functional requirement - 10.2. An actor who is authenticated as an administrator must be able to manage
		 * the catalogue of positions, which includes listing, showing, creating, updating, and deleting them.
		 * Positions can be deleted as long as they are not used.
		 * Data coverage : We tried to delete a position
		 * Exception expected: None. A administrator can delete it as long as it is not used.
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
	public void PositionNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93
			{
				"member1", null, null, "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A member tries to create a position.
			 * Requisite tested: Functional requirement - 10.2. An actor who is authenticated as an administrator must be able to manage
			 * the catalogue of positions, which includes listing, showing, creating, updating, and deleting them.
			 * Positions can be deleted as long as they are not used.
			 * Data coverage : We tried to create a position with 2 out of 2 valid editable attributes
			 * Exception expected: IllegalArgumentException. A member cannot create a position
			 */
			{
				"admin", null, "position1", "deleteNegative", IllegalArgumentException.class
			}
		/*
		 * Negative test: An administrator tries to delete a used position.
		 * Requisite tested: Functional requirement - 10.2. An actor who is authenticated as an administrator must be able to manage
		 * the catalogue of positions, which includes listing, showing, creating, updating, and deleting them.
		 * Positions can be deleted as long as they are not used.
		 * Data coverage : We tried to delete a position
		 * Exception expected: IllegalArgumentException. A administrator cannot delete a used position.
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
				final Position position = this.positionService.create();
				position.setNameEN("PositionTest");
				position.setNameES("PruebaPosicion");
				this.positionService.save(position);

			} else if (operation.equals("deletePositive")) {
				final Position position = this.positionService.findOne(this.getEntityId(id));
				this.positionService.delete(position);
			} else if (operation.equals("createNegative")) {
				final Position position = this.positionService.create();
				position.setNameEN("PositionTest");
				position.setNameES("PruebaPosicion");
				this.positionService.save(position);
			} else if (operation.equals("deleteNegative")) {
				final Position position = this.positionService.findOne(this.getEntityId(id));
				this.positionService.delete(position);
			}
			this.positionService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
