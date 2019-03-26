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

import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;
import domain.Proclaim;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ProclaimServiceTest extends AbstractTest {

	// System under test: Proclaim ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ProclaimService	proclaimService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void ProclaimPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72

			{
				"chapter1", null, null, "create", null
			},
		/*
		 * Positive test: A chapter creates his proclaim.
		 * Requisite tested: Functional requirement - 17.1 An actor who is authenticated as a chapter must be
		 * able to publish a proclaim.
		 * Data coverage : We created a proclaim with a past publicationMoment and a description.
		 * Exception expected: None. A Chapter can create/publish proclaims.
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
	protected void templatePositiveTest(final String username, final String ch, final String proclaimId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				Proclaim proclaim;

				proclaim = this.proclaimService.create();
				proclaim.setDescription("test");
				proclaim.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
				this.proclaimService.save(proclaim);

			}
			this.proclaimService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void ProclaimNegativeTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 94.7% | Covered Instructions 108 | Missed Instructions 6 | Total Instructions 114

			{
				"chapter1", null, null, "create", AssertionError.class
			},
			/*
			 * Negative: It is tried to create a proclaim without chapter.
			 * Requisite tested: Functional requirement - 17.1 An actor who is authenticated as a chapter must be
			 * able to publish a proclaim.
			 * Data coverage : We created a proclaim with 2 out of 2 valid attributes.
			 * Exception expected: AssertionError. A proclaim must be created by an existing chapter.
			 */
			{

				"chapter2", "chapter1", null, "create", IllegalArgumentException.class
			}, /*
				 * Negative: It is tried to create a proclaim by a non principal chapter
				 * Requisite tested: Functional requirement - 17.1 An actor who is authenticated as a chapter must be
				 * able to publish a proclaim.
				 * Data coverage : We created a proclaim with 2 out of 2 valid attributes.
				 * Exception expected: IllegalArgumentException. A proclaim must be created by a chapter principal
				 */
			{
				"chapter1", "chapter1", null, "createPastDate", ConstraintViolationException.class
			}, /*
				 * Negative: It is tried to create a proclaim with a future publicationMoment
				 * Requisite tested: Functional requirement - 17.1 An actor who is authenticated as a chapter must be
				 * able to publish a proclaim.
				 * Data coverage : We created a proclaim with a 1 invalid out of 2 editable attributes .
				 * Exception expected: ConstraintViolationException. A proclaim must be created with a past publicationMoment.
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

	protected void templateNegativeTest(final String username, final String ch, final String proclaimId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			if (operation.equals("create")) {
				Proclaim proclaim;
				Chapter chapter;

				proclaim = this.proclaimService.create();
				chapter = (Chapter) this.actorService.findOne(this.getEntityId(ch));
				proclaim.setDescription("test");
				proclaim.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
				proclaim.setChapter(chapter);
				this.proclaimService.save(proclaim);

			} else if (operation.equals("createPastDate")) {
				Proclaim proclaim;
				Chapter chapter;

				proclaim = this.proclaimService.create();
				chapter = (Chapter) this.actorService.findOne(this.getEntityId(ch));
				proclaim.setChapter(chapter);
				proclaim.setPublicationMoment(new Date(System.currentTimeMillis() + 2000));
				proclaim.setDescription("test");

				this.proclaimService.save(proclaim);

			}
			this.proclaimService.flush();
			super.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
