
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.MiscellaneousRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// System under test: MiscellaneousRecord ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService				brotherhoodService;
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	@Test
	public void MiscellaneousRecordPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94,1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102
			{
				"brotherhood1", null, "miscellaneousRecord1", "edit", null
			},
			/*
			 * Positive test: A brotherhood edits his miscellaneousRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Exception expected: None. A Brotherhood can edit his miscellaneousRecords.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (description) with valid data.
			 * Exception expected: None. A brotherhood can edit his data.
			 */

			{
				"brotherhood1", null, "miscellaneousRecord1", "delete", null
			},
			/*
			 * Positive: A brotherhood tries to delete a miscellaneousRecord
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : We deleted an miscellaneousRecord .
			 * Exception expected: None. A Brotherhood can delete his miscellaneousRecords.
			 */
			{
				"brotherhood1", null, null, "create", null
			}
		/*
		 * Positive: A brotherhood tries to create a Miscellaneous Record
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We created a miscellaneousRecord with valid parameters.
		 * Exception expected: None. A Brotherhood can create miscellaneousRecords.
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
	public void MiscellaneousRecordNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76
			{
				"brotherhood2", null, "miscellaneousRecord2", "edit2", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to edit a miscellaneousRecord that not owns.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage :We tried to edit a miscellaneous record with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not edit miscellaneousRecords from another brotherhood.
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
				final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(this.getEntityId(id));
				miscellaneousRecord.setDescription("Editing my miscellaneousRecord");

				this.miscellaneousRecordService.save(miscellaneousRecord);

			} else if (operation.equals("edit2")) {
				final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(this.getEntityId(id));
				miscellaneousRecord.setTitle("Failing test");

				this.miscellaneousRecordService.save(miscellaneousRecord);

			} else if (operation.equals("create")) {
				final MiscellaneousRecord mr = this.miscellaneousRecordService.create();
				mr.setTitle("Positive create");
				mr.setDescription("description");

				this.miscellaneousRecordService.save(mr);

			} else if (operation.equals("delete")) {
				final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(this.getEntityId(id));

				this.miscellaneousRecordService.delete(miscellaneousRecord);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
