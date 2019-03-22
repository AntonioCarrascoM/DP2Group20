
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
public class AdministratorServiceTest extends AbstractTest {

	// System under test: Administrator ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	//	@Autowired
	//	private AdministratorService	administratorService;

	@Autowired
	private AreaService			areaService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	@Test
	public void AdministratorPositiveTest() {
		final Object testingData[][] = {
			{
				"b1", 0.3333, null
			}, {
				"b4", 1.0, null
			}, {
				"a1", 0.75, null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Double) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	@Test
	public void AdministratorNegativeTest() {
		final Object testingData[][] = {
			{
				"c1", 0.0, IllegalArgumentException.class
			}, {
				"b4", 0.0, IllegalArgumentException.class
			}, {
				"a1", 0.0, IllegalArgumentException.class
			}
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
