
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.PeriodRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PeriodRecordServiceTest extends AbstractTest {

	// System under test: PeriodRecord ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private PeriodRecordService	periodRecordService;


	@Test
	public void PeriodRecordPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94,1% | Covered Instructions 96 | Missed Instructions 6 | Total Instructions 102
			{
				"brotherhood1", null, "periodRecord1", "edit", null
			},
			/*
			 * Positive test: A brotherhood edits his periodRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : From 3 editable attributes we tried to edit 1 editable attribute (description) with valid data.
			 * Exception expected: None. A Brotherhood can edit his periodRecords.
			 */

			{
				"brotherhood1", null, "periodRecord1", "delete", null
			},
			/*
			 * Positive: A brotherhood tries to delete a periodRecord
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : We deleted a periodRecord .
			 * Exception expected: None. A Brotherhood can delete his periodRecords.
			 */
			{
				"brotherhood1", null, null, "create", null
			}
		/*
		 * Positive: A brotherhood tries to create a Period Record
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We created a periodRecord with 5 out of 5 valid parameters.
		 * Exception expected: None. A Brotherhood can create periodRecords.
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
	public void PeriodRecordNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93
			{
				"brotherhood2", null, "periodRecord2", "edit2", IllegalArgumentException.class
			},
			/*
			 * Negative: A brotherhood tries to edit a periodRecord that not owns.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : We tried to edit 1 out of 5 editable attributes with an user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Brotherhood can not edit periodRecords from another brotherhood.
			 */
			{
				"brotherhood1", null, null, "create2", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to create a period record with wrong parameters.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : A brotherhood tries to create a period record with 1 invalid attribute out of 5.
		 * Exception expected: IllegalArgumentException. Start year must be smaller than end year.
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
				final PeriodRecord periodRecord = this.periodRecordService.findOne(this.getEntityId(id));
				periodRecord.setDescription("Editing my periodRecord");

				this.periodRecordService.save(periodRecord);

			} else if (operation.equals("edit2")) {
				final PeriodRecord periodRecord = this.periodRecordService.findOne(this.getEntityId(id));
				periodRecord.setTitle("Failing test");

				this.periodRecordService.save(periodRecord);

			} else if (operation.equals("create")) {
				final PeriodRecord pr = this.periodRecordService.create();
				pr.setTitle("Positive creation");
				pr.setDescription("description");
				pr.setStartYear(2012);
				pr.setEndYear(2016);
				pr.setPhotos("https://www.morephotitos.org");

				this.periodRecordService.save(pr);

			} else if (operation.equals("create2")) {
				final PeriodRecord pr = this.periodRecordService.create();
				pr.setTitle("Negative creation");
				pr.setDescription("description");
				pr.setStartYear(2018);
				pr.setEndYear(2016);
				pr.setPhotos("https://www.morephotitos.org");

				this.periodRecordService.save(pr);

			} else if (operation.equals("delete")) {
				final PeriodRecord periodRecord = this.periodRecordService.findOne(this.getEntityId(id));

				this.periodRecordService.delete(periodRecord);

			}

			this.periodRecordService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
