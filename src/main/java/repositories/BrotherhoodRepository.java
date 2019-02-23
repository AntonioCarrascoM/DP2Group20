
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
	@Query("select b from Brotherhood b order by b.enrolments.size desc")
	Collection<Brotherhood> largestBrotherhoods();

	//The smallest brotherhoods
	@Query("select b from Brotherhood b order by b.enrolments.size asc")
	Collection<Brotherhood> smallestBrotherhoods();
}
