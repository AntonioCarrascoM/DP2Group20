
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brotherhood;

@Repository
public interface BrotherhoodRepository extends JpaRepository<Brotherhood, Integer> {

	//Returns the collection of spammer brotherhoods.
	@Query("select b from Brotherhood b where b.spammer = true")
	Collection<Brotherhood> spammerBrotherhoods();

	//The largest brotherhoods
	@Query("select b.title from Brotherhood b order by b.enrolments.size desc")
	Collection<Brotherhood> largestBrotherhoods();

	//The smallest brotherhoods
	@Query("select b.title from Brotherhood b order by b.enrolments.size asc")
	Collection<Brotherhood> smallestBrotherhoods();

	//Returns the enrolmentable brotherhoods given a member id
	@Query("select e.brotherhood from Member m join m.enrolments e where m.id=?1 and e.dropOutMoment=null")
	Collection<Brotherhood> nonEnrolmentableBrotherhoods(int memberId);

	//Returns the not enrolmentable members given a brotherhood id
	@Query("select e.member from Brotherhood b join b.enrolments e where b.id=?1 and e.dropOutMoment=null")
	Collection<Brotherhood> nonEnrolmentableMembers(int brotherhoodId);

	//Returns the brotherhoods given a member id
	@Query("select e.brotherhood from Member m join m.enrolments e where m.id=?1")
	Collection<Brotherhood> enrolmentMemberBrotherhoods(int memberId);
}
