
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	//Returns the collection of spammer members.
	@Query("select m from Member m where m.spammer = true")
	Collection<Member> spammerMembers();

	//The average, the minimum, the maximum, and the standard deviation of the number of members per brotherhood.
	@Query("select avg(b.enrolments.size), min(b.enrolments.size), max(b.enrolments.size), stddev(b.enrolments.size) from Brotherhood b")
	Double[] minMaxAvgStddevMemberPerBrotherhood();

	//Returns the member related with a certain finder.
	@Query("select m from Member m where m.finder.id=?1")
	Member memberByFinder(int id);

	//The listing of members who have got at least 10% the maximum number of request to march accepted
	@Query("select m.name from Member m where (select count (r1) from Member m1 join m1.requests r1 where r1.status='1' and m1.id=m.id)*1.0 > (select avg((select count(r2) from Request r2 where r2.status='1' and r2.member.id=m3.id)*1.0) from Member m3)*1.1")
	Collection<Member> membersWithApprovedRequestsThanAvg();

	//The listing of active members of a certain brotherhood
	@Query("select distinct e.member from Enrolment e where e.brotherhood.id=?1 and e.dropOutMoment is null")
	Collection<Member> activeMembersOfBrotherhood(int id);

}
