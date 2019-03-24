
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.LinkRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LinkRecordServiceTest extends AbstractTest {

	// System under test: LinkRecord ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService		brotherhoodService;
	@Autowired
	private LinkRecordService	linkRecordService;


	@Test
	public void LinkRecordPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.9% | Covered Instructions 79 | Missed Instructions 6 | Total Instructions 85
			{
				"brotherhood1", null, "linkRecord1", "edit", null
			},
			/*
			 * Positive test: A brotherhood edits his linkRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Exception expected: None. A Brotherhood can edit his linkRecords.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (link) with valid data.
			 * Exception expected: None. A brotherhood can edit his data.
			 */

			{
				"brotherhood1", null, null, "create", null
			}
		/*
		 * Positive: A brotherhood tries to create a Link Record
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We created a linkRecord with valid parameters.
		 * Exception expected: None. A Brotherhood can create linkRecords.
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
	public void LinkRecordNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 92.1% | Covered Instructions 70 | Missed Instructions 6 | Total Instructions 76
			{
				"brotherhood2", null, "linkRecord2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to delete a linkRecord that not owns.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage :We tried to delete a link record with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not delete linkRecords from another brotherhood.
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
				final LinkRecord linkRecord = this.linkRecordService.findOne(this.getEntityId(id));
				linkRecord.setLink("https://www.mozilla.org");

				this.linkRecordService.save(linkRecord);

			} else if (operation.equals("create")) {
				final LinkRecord lr = this.linkRecordService.create();
				lr.setTitle("Positive create");
				lr.setDescription("description");
				lr.setLink("https://www.mozilla.org");

				this.linkRecordService.save(lr);

			} else if (operation.equals("delete")) {
				final LinkRecord linkRecord = this.linkRecordService.findOne(this.getEntityId(id));

				this.linkRecordService.delete(linkRecord);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
