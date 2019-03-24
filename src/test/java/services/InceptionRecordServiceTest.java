
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.InceptionRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class InceptionRecordServiceTest extends AbstractTest {

	// System under test: InceptionRecord ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService			brotherhoodService;
	@Autowired
	private InceptionRecordService	inceptionRecordService;


	@Test
	public void InceptionRecordPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89
			{
				"brotherhood1", null, "inceptionRecord1", "edit", null
			},
			/*
			 * Positive test: A brotherhood edits his inceptionRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Exception expected: None. A Brotherhood can edit his inceptionRecords.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (description) with valid data.
			 * Exception expected: None. A brotherhood can edit his data.
			 */

			{
				"brotherhood2", null, "inceptionRecord2", "edit2", null
			},

			/*
			 * Positive test: A brotherhood edits his inceptionRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Exception expected: None. A Brotherhood can edit his inceptionRecords.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (title) with valid data.
			 * Exception expected: None. A brotherhood can edit his data.
			 */

			{
				"brotherhood1", null, "inceptionRecord1", "edit3", null
			},

			/*
			 * Positive test: A brotherhood edits his inceptionRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Exception expected: None. A Brotherhood can edit his inceptionRecords.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (photos) with valid data.
			 * Exception expected: None. A brotherhood can edit his data.
			 */
			{
				"brotherhood1", null, "inceptionRecord1", "delete", null
			},
			/*
			 * Positive: A brotherhood tries to delete a inceptionRecord
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : We deleted an inceptionRecord .
			 * Exception expected: None. A Brotherhood can delete his inceptionRecords.
			 */
			{
				"brotherhood1", "brotherhood1", null, "create", null
			}
		/*
		 * Positive: A brotherhood tries to create a inceptionRecord
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We created a inceptionRecord with an accepted parade (parade3) and a valid VISA credit card.
		 * Exception expected: None. A Brotherhood can create inceptionRecords.
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
	public void InceptionRecordNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.1% | Covered Instructions 95 | Missed Instructions 6 | Total Instructions 101
			{
				"brotherhood1", null, "inceptionRecord2", "edit4", IllegalArgumentException.class
			},
			/*
			 * Negative: A brotherhood tries to edit a inceptionRecord that not owns.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (photos) with a user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Brotherhood can not edit inceptionRecords from another brotherhood.
			 */
			//			{
			//				"brotherhood1", "notAnUrl", null, "create2", ConstraintViolationException.class
			//			},
			/*
			 * Negative: A brotherhood tries to create an invalid inceptionRecord
			 * Requisite tested: Functional requirement - 16.1 An actor who is autenticated as a brotherhood must be
			 * able to create inceptionRecords
			 * Data coverage : We tried to create a inceptionRecord with an invalid Photos url.
			 * Exception expected: ConstraintViolationException. The InceptionRecord photos field must be a valid url.
			 */
			{
				"brotherhood1", null, "inceptionRecord2", "delete", IllegalArgumentException.class
			},
		/*
		 * Negative: A brotherhood tries to delete a inceptionRecord that not owns.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage :We tried to delete an inception with an user that is not the owner.
		 * Exception expected: IllegalArgumentException. A Brotherhood can not delete inceptionRecords from another brotherhood.
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
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(this.getEntityId(id));
				inceptionRecord.setDescription("New test description");

				this.inceptionRecordService.save(inceptionRecord);

			} else if (operation.equals("edit2")) {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(this.getEntityId(id));
				inceptionRecord.setTitle("Testing title edition");

				this.inceptionRecordService.save(inceptionRecord);

			} else if (operation.equals("edit3")) {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(this.getEntityId(id));
				inceptionRecord.setPhotos("https://www.photitos.com");

				this.inceptionRecordService.save(inceptionRecord);

			} else if (operation.equals("edit4")) {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(this.getEntityId(id));
				inceptionRecord.setTitle("This is not going to work");

				this.inceptionRecordService.save(inceptionRecord);

			} else if (operation.equals("create")) {
				final InceptionRecord ir = this.inceptionRecordService.create();
				ir.setTitle("Positive create");
				ir.setDescription("description");
				ir.setPhotos("https://www.photos.com");

				this.inceptionRecordService.save(ir);

			} else if (operation.equals("create2")) {
				final InceptionRecord ir = this.inceptionRecordService.create();
				ir.setTitle("Negative creation");
				ir.setDescription("description");
				ir.setPhotos(st);

				this.inceptionRecordService.save(ir);

			} else if (operation.equals("delete")) {
				final InceptionRecord inceptionRecord = this.inceptionRecordService.findOne(this.getEntityId(id));

				this.inceptionRecordService.delete(inceptionRecord);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
