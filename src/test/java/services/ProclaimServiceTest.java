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
	private ProclaimService	proclaimService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void driver() {
		final Object testingData[][] = {

			{
				"chapter1", "chapter1", null, "create", null
			}, //A chapter creates a proclaim
			{
				"chapter1", null, null, "create", AssertionError.class
			}, //Proclaim can't be created without chapter
			{

				"chapter2", "chapter1", null, "create", IllegalArgumentException.class
			}, //The creator of a proclaim must be the principal
			{
				"chapter1", "chapter1", null, "createPastDate", ConstraintViolationException.class
			}, //Date must be in the past
			{
				"chapter2", null, "proclaim1", "edit", IllegalArgumentException.class
			}
		//A chapter can't edit a proclaim that doesn't belong to him
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

	protected void template(final String username, final String ch, final String proclaimId, final String operation, final Class<?> expected) {
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
			} else if (operation.equals("edit")) {
				Proclaim proclaim;

				proclaim = this.proclaimService.findOne(this.getEntityId(proclaimId));

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
