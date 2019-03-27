
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Box;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BoxServiceTest extends AbstractTest {

	// System under test: Box ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ActorService		actorService;
	@Autowired
	private BoxService			boxService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@Test
	public void BoxPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"brotherhood1", null, "brotherhood1", "create", null
			},
		/*
		 * Positive test: A brotherhood create a box.
		 * Requisite tested: Functional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
		 * manage his or her message boxes, except for the system boxes.
		 * Data coverage : We created a box with valid parameters.
		 * Exception expected: None. A Brotherhood can create boxs.
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
	public void BoxNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"brotherhood1", null, "inBox2", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative: A brotherhood tries to edit a system box.
			 * Requisite tested: Functional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
			 * manage his or her message boxes, except for the system boxes.
			 * Data coverage : We tried to edit a system box.
			 * Exception expected: IllegalArgumentException. A Brotherhood cannot edit system boxes
			 */
			{
				"brotherhood1", null, "outBox2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to delete a system box.
		 * Requisite tested: Functional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
		 * manage his or her message boxes, except for the system boxes.
		 * Data coverage : We tried to delete a system box.
		 * Exception expected: IllegalArgumentException. A Brotherhood cannot delete system boxes
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
			if (operation.equals("create")) {
				final Box box = this.boxService.create(this.actorService.findByPrincipal());
				box.setName("testBox");
				box.setSystem(false);

				this.boxService.save(box);

			} else if (operation.equals("edit")) {

				final Box box = this.boxService.findOne(this.getEntityId(id));
				box.setName("testBox");
				this.boxService.save(box);

			} else if (operation.equals("delete")) {
				final Box box = this.boxService.findOne(this.getEntityId(id));

				this.boxService.delete(box);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
