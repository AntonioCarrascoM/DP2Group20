
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {

	// System under test: Dashboard ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AreaService			areaService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	@Test
	public void DashboardPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 100.0% | Covered Instructions 74 | Missed Instructions 0 | Total Instructions 74

			{
				"b1", 0.3333, null
			}, {
				"b4", 1.0, null
			}, {
				"a1", 0.75, null
			}
		/*
		 * Positive test: Result expected of queries from dashboard
		 * Requisite tested: Functional requirement - The ratio of areas that are not co-ordinated by any chapters.
		 * - The ratio of parades in draft mode versus parades in final mode.
		 * - The ratio of active sponsorships.
		 * Data coverage : From 11 queries from dashboard we checked 3 with valid datas.
		 * Exception expected: None.
		 */
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	@Test
	public void DashboardNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 100.0% | Covered Instructions 86 | Missed Instructions 0 | Total Instructions 86

			{
				"c1", 0.0, IllegalArgumentException.class
			}, {
				"b4", 0.0, IllegalArgumentException.class
			}, {
				"a1", 0.0, IllegalArgumentException.class
			}
		/*
		 * Negative test: Result unexpected of queries from dashboard
		 * Requisite tested: Functional requirement - The ratio of areas that are not co-ordinated by any chapters.
		 * - The ratio of parades in draft mode versus parades in final mode.
		 * - The ratio of active sponsorships.
		 * Data coverage : From 11 queries from dashboard we checked 3 with invalid datas.
		 * Exception expected: IllegalArgumentException.
		 */
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void template(final String query, final Double value, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			if (query.equals("c1"))
				Assert.isTrue(value.equals(this.areaService.ratioAreasNotCoordinated()));
			if (query.equals("b4"))
				Assert.isTrue(value.equals(this.paradeService.ratioParadesInDraftModeVsFinalMode()));
			if (query.equals("a1"))
				Assert.isTrue(value.equals(this.sponsorshipService.ratioOfActiveSponsorships()));
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);

	}
}
