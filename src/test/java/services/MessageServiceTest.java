
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Message;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test: Message ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MessageService		messageService;


	@Test
	public void MessagePositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"brotherhood3", null, "message1", "edit", null
			},
		/*
		 * Positive test: A brotherhood edits his messages.
		 * Requisite tested: Functional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
		 * manage his or her message boxes, except for the system boxes.
		 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (body) with valid data.
		 * Exception expected: None. A Brotherhood can edit his messages.
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
	public void MessageNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"brotherhood1", null, "message1", "edit", IllegalArgumentException.class
			},
			/*
			 * Negative: A brotherhood tries to edit a message that not owns.
			 * Requisite tested: FFunctional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
			 * manage his or her message boxes, except for the system boxes.
			 * Exception expected: IllegalArgumentException. A Brotherhood can not edit messages from another brotherhood.
			 * Data coverage : From 3 editable attributes we tried to edit 1 attribute (photos) with a user that is not the owner.
			 */
			{
				"brotherhood1", null, "message1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to delete a message that not owns.
		 * Requisite tested: Functional requirement - 27.2 An actor who is authenticated as a brotherhood must be able to
		 * manage his or her message boxes, except for the system boxes.
		 * Data coverage :We tried to delete an inception with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not delete messages from another brotherhood.
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
				final Message message = this.messageService.findOne(this.getEntityId(id));
				message.setBody("Testing body edition");

				this.messageService.save(message);

			} else if (operation.equals("delete")) {
				final Message message = this.messageService.findOne(this.getEntityId(id));

				this.messageService.delete(message);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
