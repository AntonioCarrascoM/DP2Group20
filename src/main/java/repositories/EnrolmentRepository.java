
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Enrolment;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Integer> {

	//Returns the received endorsements for a certain member
	@Query("select e from Enrolment e where e.brotherhoodToMember=true and e.member.id=?1")
	Collection<Enrolment> receivedEnrolmentsFromMember(int actorId);

	//Returns the received endorsements for a certain brotherhood
	@Query("select e from Enrolment e where e.brotherhoodToMember=false and e.brotherhood.id=?1")
	Collection<Enrolment> receivedEnrolmentsFromBrotherhood(int actorId);

	// A histogram of positions.
	@Query("select count(e) from Enrolment e join e.position p group by p.nameEN order by e.position.id")
	Collection<Integer> histogramOfPositions();

	//Returns the enrolments given a position 
	@Query("select distinct e from Enrolment e where e.position.id =?1")
	Collection<Enrolment> getEnrolmentsFromAPosition(int positionId);

}
