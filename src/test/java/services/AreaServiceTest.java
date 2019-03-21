/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Area;
import domain.Brotherhood;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AreaServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that 
	// JUnit works well in this project.  Just righ-click this class and run 
	// it using JUnit.

	//	@Test
	//	public void SamplePositiveTest() {
	//		Assert.isTrue(true);
	//	}
	//
	//	@Test(expected = IllegalArgumentException.class)
	//	public void SampleNegativeTest() {
	//		Assert.isTrue(false);
	//	}

	@Autowired
	private AreaService			areaService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@Test
	public void AreaPositiveTest() {
		final Object testingData[][] = {

			{
				"admin1", "admin1", null, "create", null
			}, //A admin creates a area

		//A chapter can't edit a proclaim that doesn't belong to him
		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templatePositiveTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}
	protected void templatePositiveTest(final String username, final String ad, final String areaId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				Area area;
				final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();

				area = this.areaService.create();
				area.setBrotherhoods(brotherhoods);
				area.setName("test");
				area.setPictures("pictures");
				this.areaService.save(area);

			}
			this.areaService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void AreaNegativeTest() {
		final Object testingData[][] = {

			{
				"admin1", null, null, "create", AssertionError.class
			}, //Area can't be created without admin
			{

				"admin2", "admin1", null, "create", IllegalArgumentException.class
			}, //The creator of a area must be the principal

		};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.templateNegativeTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			} finally {
				super.rollbackTransaction();
			}
	}

	protected void templateNegativeTest(final String username, final String ad, final String proclaimId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				Area area;
				final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();

				area = this.areaService.create();
				area.setBrotherhoods(brotherhoods);
				area.setName("test");
				area.setPictures("pictures");
				this.areaService.save(area);

			}
			this.areaService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
