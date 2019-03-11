
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//Returns the collection of suspicious administrators.
	@Query("select a from Administrator a where a.spammer = true")
	Collection<Administrator> spammerAdministrators();
}
