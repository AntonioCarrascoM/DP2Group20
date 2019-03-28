
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Enrolment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EnrolmentServiceTest extends AbstractTest {

	// System under test: Enrolment ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private EnrolmentService	enrolmentService;

	@Autowired
	private PositionService		positionService;


	@Test
	public void EnrolmentPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92,1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76
			{
				"brotherhood2", "member1", "enrolment3", "delete", null
			},
		/*
		 * Positive: A brotherhood tries to delete a enrolment.
		 * Requisite tested: Functional requirement -10.3 An actor who is authenticated as a brotherhood must be able to
		 * Manage the members of the brotherhood, which includes listing, showing, enrolling, and removing them. When a member is enrolled, a position must be selected by the brotherhood.
		 * Data coverage : We deleted an enrolment.
		 * Exception expected: None. A Brotherhood can delete his enrolments.
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
	public void EnrolmentNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76
			{
				"member1", null, "enrolment1", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to delete a enrolment that doesn't have a dropOutMoment.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage :We tried to edit 1 out of 2 miscellaneous record attributes with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not delete an enrolment if it doesn't have a dropOutMoment
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
				final Enrolment enrolment = this.enrolmentService.findOne(this.getEntityId(id));
				enrolment.setPosition(this.positionService.create());

				this.enrolmentService.save(enrolment);

			} else if (operation.equals("create")) {
				final Enrolment e = this.enrolmentService.create(this.getEntityId(username));
				e.setPosition(this.positionService.create());

				this.enrolmentService.save(e);

			} else if (operation.equals("delete")) {
				final Enrolment enrolment = this.enrolmentService.findOne(this.getEntityId(id));
				this.enrolmentService.delete(enrolment);

			}

			this.enrolmentService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
