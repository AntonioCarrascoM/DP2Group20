
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

	//The brotherhood with the largest history.
	@Query("select b.title from Brotherhood b order by (select count(r) from Record r where r.brotherhood.id=b.id)*1. desc")
	Collection<Brotherhood> largestBrotherhoodsByHistory();

	//The brotherhoods whose history is larger than the average.
	@Query("select b.title from Brotherhood b where ((select count(r1) from Record r1 where r1.brotherhood.id=b.id)*1.)>(select avg((select count(r2) from Record r2 where r2.brotherhood.id=b2.id)*1.0) from Brotherhood b2) order by (select count(r3) from Record r3 where r3.brotherhood.id=b.id)*1. desc")
	Collection<Brotherhood> largestBrotherhoodsByHistoryThanAvg();

}
