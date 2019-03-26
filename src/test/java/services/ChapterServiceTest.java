
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chapter;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChapterServiceTest extends AbstractTest {

	// System under test: Chapter ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private ChapterService	chapterService;


	@Test
	public void ChapterPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"chapter1", null, "chapter1", "edit", null
			}
		/*
		 * Positive test: A chapter edit his data.
		 * Requisite tested: Functional requirement - An actor who is autenticated must be able to edit his personal data.
		 * Data coverage : From 7 editable atributes we tried to edit 1 atribute (name) with valid data.
		 * Exception expected: None. A chapter can edit his data.
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
	public void ChapterNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 72
			{
				"chapter1", null, "chapter2", "edit2", IllegalArgumentException.class
			},
			/*
			 * Negative test: User chapter1 tries to edit personal data of user chapter2.
			 * Requisite tested: Functional requirement - An actor who is autenticated must be able to edit his personal data.
			 * Data coverage: From 7 editable atributes we tried to edit 1 atribute (name) with another user.
			 * IllegalArgumentException: Exception expected
			 * Exception expected: IllegalArgumentException A chapter cannot edit others personal data.
			 */
			{
				null, " ", null, "create", ConstraintViolationException.class
			}
		/*
		 * Negative test: Registering a chapter with invalid username.
		 * Requisite tested: Functional requirement - 15 An actor who is not autenticated must be able to register as Chapter
		 * Data coverage: From 9 editable atributes we tried to create a chapter with 1 atribute (username) with invalid data.
		 * Exception expected: ConstraintViolationException. Username cannot be blank.
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
	protected void template(final String username, final String st, final String chapterId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			if (operation.equals("create")) {
				final Chapter chapter = this.chapterService.create();

				chapter.setName("nombre");
				chapter.setMiddleName("mname");
				chapter.setSurname("sname");
				chapter.setAddress("calle");
				chapter.setPhoto("");
				chapter.setPhone("666666666");
				chapter.getUserAccount().setPassword("test");
				chapter.getUserAccount().setUsername(st);
				chapter.setEmail("email@email.com");
				chapter.setTitle("This a title");
				this.chapterService.save(chapter);
			}
			super.authenticate(username);
			if (operation.equals("edit")) {
				Chapter chapter;
				chapter = this.chapterService.findOne(this.getEntityId(chapterId));

				chapter.setName("Test");
				this.chapterService.save(chapter);
			} else if (operation.equals("edit2")) {
				Chapter chapter;
				chapter = this.chapterService.findOne(this.getEntityId(chapterId));

				chapter.setName("Test");
				this.chapterService.save(chapter);
			}
			this.chapterService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
