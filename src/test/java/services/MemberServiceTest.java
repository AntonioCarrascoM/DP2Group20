
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Member;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MemberServiceTest extends AbstractTest {

	// System under test: Member ------------------------------------------------------

	// Tests ------------------------------------------------------------------
	// PLEASE READ
	// The Sentence coverage has been obtained with the tool EclEmma from Eclipse. 
	// Since having one @Test for every case is not optimal we divided the user cases in two cases. Positives and Negatives.

	@Autowired
	private MemberService	memberService;


	@Test
	public void MemberPositiveTest() {
		final Object testingData[][] = {
			//Total sentence coverage : Coverage 91.7% | Covered Instructions 66 | Missed Instructions 6 | Total Instructions 72
			{
				"member1", null, "member1", "edit", null
			}
		/*
		 * Positive test: A member edit his data.
		 * Requisite tested: Functional requirement - An actor who is authenticated must be able to edit his personal data.
		 * Data coverage : From 7 editable attributes we tried to edit 1 attribute (name) with valid data.
		 * Exception expected: None. A member can edit his data.
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
	public void MemberNegativeTest() {
		final Object testingData[][] = {
			//Total Sentence Coverage: Coverage 93.5% | Covered Instructions 87 | Missed Instructions 6 | Total Instructions 93
			{
				"member1", null, "member2", "edit2", IllegalArgumentException.class
			},
			/*
			 * Negative test: User member1 tries to edit personal data of user member2.
			 * Requisite tested: Functional requirement - An actor who is authenticated must be able to edit his personal data.
			 * Data coverage: From 7 editable attributes we tried to edit 1 attribute (name) with another user.
			 * Exception expected: IllegalArgumentException A member cannot edit others personal data.
			 */
			{
				null, " ", null, "create", ConstraintViolationException.class
			}
		/*
		 * Negative test: Registering a member with invalid username.
		 * Requisite tested: Functional requirement - 15 An actor who is not authenticated must be able to register as Member.
		 * Data coverage: From 9 editable attributes we tried to create a member with 1 attribute (username) with invalid data.
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
	protected void template(final String username, final String st, final String memberId, final String operation, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);
			if (operation.equals("create")) {
				final Member member = this.memberService.create();

				member.setName("nombre");
				member.setMiddleName("mname");
				member.setSurname("sname");
				member.setAddress("calle");
				member.setPhoto("");
				member.setPhone("666666666");
				member.getUserAccount().setPassword("test");
				member.getUserAccount().setUsername(st);
				member.setEmail("email@email.com");
				this.memberService.save(member);
			}
			if (operation.equals("edit")) {
				Member member;
				member = this.memberService.findOne(this.getEntityId(memberId));

				member.setName("Test");
				this.memberService.save(member);
			} else if (operation.equals("edit2")) {
				Member member;
				member = this.memberService.findOne(this.getEntityId(memberId));

				member.setName("Test");
				this.memberService.save(member);
			}
			this.memberService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
