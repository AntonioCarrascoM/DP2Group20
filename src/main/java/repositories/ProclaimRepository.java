
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Proclaim;

@Repository
public interface ProclaimRepository extends JpaRepository<Proclaim, Integer> {

	//Retrieves a collection of proclaims for a certain chapter.
	@Query("select p from Proclaim p where p.chapter.id=?1")
	Collection<Proclaim> getProclaimsForChapter(int id);
}
