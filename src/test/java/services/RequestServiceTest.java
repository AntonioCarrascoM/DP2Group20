
package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Request;
import domain.Status;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RequestServiceTest extends AbstractTest {

	// System under test: Member ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	@Autowired
	private MemberService	memberService;
	@Autowired
	private RequestService	requestService;
	@Autowired
	private ParadeService	paradeService;


	@Test
	public void RequestPositiveTest() {
		final Object testingData[][] = {

			{
				"member1", null, "parade3", "create", null
			}
		//Positive test: A member tries to create a request
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
	public void RequestNegativeTest() {
		final Object testingData[][] = {

			{
				"member1", null, "parade3", "create2", ConstraintViolationException.class
			}
		//Negative test: A member tries to create an invalid request
		//Exception expected: ConstraintViolationException. CustomRow cannot be negative.
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

			if (operation.equals("create")) {
				final Request s = this.requestService.create();

				s.setStatus(Status.APPROVED);
				s.setCustomRow(3);
				s.setCustomColumn(3);
				s.setReason("This is a reason");
				s.setParade(this.paradeService.findOne(this.getEntityId(id)));

				this.requestService.save(s);
			} else if (operation.equals("create2")) {
				final Request s = this.requestService.create();

				s.setStatus(Status.APPROVED);
				s.setCustomRow(-1);
				s.setCustomColumn(3);
				s.setReason("This is a reason");
				s.setParade(this.paradeService.findOne(this.getEntityId(id)));

				this.requestService.save(s);
			}
			this.memberService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
