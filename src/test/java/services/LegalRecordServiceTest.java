
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.LegalRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LegalRecordServiceTest extends AbstractTest {

	// System under test: LegalRecord ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SponsorService		brotherhoodService;
	@Autowired
	private LegalRecordService	legalRecordService;


	@Test
	public void LegalRecordPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89
			{
				"brotherhood1", null, "legalRecord1", "edit", null
			},
			/*
			 * Positive test: A brotherhood edits his legalRecord.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : From 3 editable atributes we tried to edit 1 atribute (description) with valid data.
			 * Exception expected: None. A Brotherhood can edit his legalRecords.
			 */

			{
				"brotherhood1", null, "legalRecord1", "delete", null
			},
		/*
		 * Positive: A brotherhood tries to delete a legalRecord.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We deleted an legalRecord.
		 * Exception expected: None. A Brotherhood can delete his legalRecords.
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
	public void LegalRecordNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93
			{
				"brotherhood2", null, "legalRecord2", "delete", IllegalArgumentException.class
			},
			/*
			 * Negative: A brotherhood tries to edit a inceptionRecord that not owns.
			 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
			 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
			 * Data coverage : We tried to edit a miscellaneous record with an user that is not the owner.
			 * Exception expected: IllegalArgumentException. A Brotherhood can not delete inceptionRecords from another brotherhood.
			 */
			{
				"brotherhood1", null, null, "create", ConstraintViolationException.class
			},
		/*
		 * Negative: A brotherhood tries to create a legalRecord with wrong parameters.
		 * Requisite tested: Functional requirement - 3. An actor who is authenticated as a brotherhood must be able to
		 * manage their history, which includes listing, displaying, creating, updating, and deleting its records.
		 * Data coverage : We brotherhood tries to create a legal record with a negative VAT number.
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
				final LegalRecord legalRecord = this.legalRecordService.findOne(this.getEntityId(id));
				legalRecord.setDescription("Editing my legalRecord");

				this.legalRecordService.save(legalRecord);

			} else if (operation.equals("create")) {
				final LegalRecord lr = this.legalRecordService.create();
				lr.setTitle("Wrong VAT");
				lr.setDescription("description");
				lr.setLegalName("Legal name");
				lr.setVatNumber(-1.0);

				this.legalRecordService.save(lr);

			} else if (operation.equals("delete")) {
				final LegalRecord legalRecord = this.legalRecordService.findOne(this.getEntityId(id));

				this.legalRecordService.delete(legalRecord);

			}

			this.brotherhoodService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
