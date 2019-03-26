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

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Area;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AreaServiceTest extends AbstractTest {

	// System under test: Area ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private AreaService	areaService;


	@Test
	public void AreaPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"admin", null, null, "create", null
			},
		/*
		 * Positive test: An admin edit an area.
		 * Requisite tested: Functional requirement - 22.1 An actor who is authenticated as an administrator must be
		 * able to manage the areas in the system, which includes updating them.
		 * Data coverage : We created an area providing two out of two possible attributes (name and pictures)
		 * Exception expected: None. An Administrator can create areas if valid attributes are provided.
		 */

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
	protected void templatePositiveTest(final String username, final String ch, final String areaId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				Area area;

				area = this.areaService.create();
				area.setName("name");
				area.setPictures("https://www.google.es");
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
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"admin", null, null, "create2", ConstraintViolationException.class
			}, /*
				 * Negative: is tried to create a area with a non valid name.
				 * Requisite tested: Functional requirement - 22.1 An actor who is authenticated as an administrator must be
				 * able to manage the areas in the system, which includes listing them, creating them, updating them, and deleting them.
				 * Data coverage : We tried to create an area with just one of its two attributes. We provided a valid url but not a valid name.
				 * Exception expected: ConstraintViolationException. An area must be created with a valid name.
				 */

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

	protected void templateNegativeTest(final String username, final String ch, final String areaId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create2")) {
				Area area;

				area = this.areaService.create();
				area.setName(null);
				area.setPictures("https://www.google.es");
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
