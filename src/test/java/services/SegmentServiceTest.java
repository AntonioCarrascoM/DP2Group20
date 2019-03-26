
package services;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Segment;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class SegmentServiceTest extends AbstractTest {

	// System under test: Segment ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private SegmentService	segmentService;


	@Test
	public void SegmentPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.3% | Covered Instructions 83 | Missed Instructions 6 | Total Instructions 89
			{
				"brotherhood1", null, "parade2", "createPositive", null
			}
			/*
			 * Positive test: A brotherhood creates a segment.
			 * Requisite tested: Functional requirement - 10.2 An actor who is authenticated as a brotherhood must be able to manage their parades,
			 * which creating them
			 * Data coverage : We tried to create a segment with 6 out of 6 valid editable attributes
			 * Exception expected: None. A Brotherhood can create a parade
			 */
			, {
				"brotherhood1", null, "parade2", "deletePositive", null
			}
		/*
		 * Positive: A Brotherhood delete his path.
		 * Requisite tested: Functional requirement - 3.3 An actor who is authenticated as a brotherhood must be
		 * able to manage the paths of their parades which includes deleting them.
		 * Data coverage : We tried to delete all their segments
		 * Exception expected: None. A Brotherhood can delete a path of their parades as long as parade is not on final mode.
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
	public void SegmentNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 93.8% | Covered Instructions 91 | Missed Instructions 6 | Total Instructions 97
			{
				"brotherhood1", null, "parade3", "createNegative", IllegalArgumentException.class
			},
			/*
			 * Negative: A Brotherhood tries to create a segment of a parade that not owns.
			 * Requisite tested: Functional requirement - 3.3 An actor who is authenticated as a brotherhood must be able to manage the
			 * paths of their parades which includes creating them.
			 * Data coverage : A Brotherhood tries to create a segment with 6 out of 6 valid editable attributes.
			 * Exception expected: IllegalArgumentException. A Brotherhood can not create segments of another brotherhood parade.
			 */
			{
				"brotherhood1", null, "parade2", "editNegative", ConstraintViolationException.class
			}
		/*
		 * Negative: A Brotherhood tries to edit an invalid segment with an invalid x destination coordinate.
		 * Requisite tested: Functional requirement - 3.3 An actor who is authenticated as a brotherhood must be able to manage the
		 * paths of their parades which includes updating them.
		 * Data coverage : A Brotherhood tries to edit 1 invalid out of 6 editable attributes.
		 * Exception expected: ConstraintViolationException. X destination coordinate must adhere to a specific pattern.
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
				final Segment segment = this.segmentService.create(this.getEntityId(id));
				segment.setOriginCoordX("-90.00");
				segment.setOriginCoordY("-180.00");
				segment.setDestinationCoordX("+90.0");
				segment.setDestinationCoordY("-127.55");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date destinationDate = sdf.parse("21/12/2020 12:34");
				final Date originDate = sdf.parse("21/10/2012 12:34");
				segment.setOriginDate(originDate);
				segment.setDestinationDate(destinationDate);
				this.segmentService.save(segment);

			} else if (operation.equals("deletePositive"))
				this.segmentService.delete(this.getEntityId(id));
			else if (operation.equals("createNegative")) {
				final Segment segment = this.segmentService.create(this.getEntityId(id));
				segment.setOriginCoordX("-90.00");
				segment.setOriginCoordY("-180.00");
				segment.setDestinationCoordX("+90.0");
				segment.setDestinationCoordY("-127.55");
				final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				final Date destinationDate = sdf.parse("21/12/2020 12:34");
				final Date originDate = sdf.parse("21/10/2012 12:34");
				segment.setOriginDate(originDate);
				segment.setDestinationDate(destinationDate);
				this.segmentService.save(segment);
			} else if (operation.equals("editNegative")) {
				final Segment segment = this.segmentService.getLastSegment(this.getEntityId(id));
				segment.setDestinationCoordX("234455");
				this.segmentService.save(segment);
			}
			this.segmentService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
