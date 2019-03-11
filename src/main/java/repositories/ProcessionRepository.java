
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Procession;

@Repository
public interface ProcessionRepository extends JpaRepository<Procession, Integer> {

	//The processions that are going to be organised in 30 days or less.
	@Query("select p from Procession p where p.moment-(CURRENT_TIMESTAMP+1) < 99000000")
	Collection<Procession> processionsBefore30Days();

	//Processions by area
	@Query("select p from Procession p join p.brotherhood b join b.area a where a.id=?1")
	Collection<Procession> processionsByArea(int id);

	//Listing of the processions with finalMode = true
	@Query("select p from Procession p where p.finalMode = true")
	Collection<Procession> getFinalProcessions();

	//Listing of processions with finalMode = true that belong to a certain brotherhood.
	@Query("select p from Procession p where p.finalMode=1 and p.brotherhood.id=?1")
	Collection<Procession> finalProcessionsForBrotherhood(int varId);

	//Processions which a member can do requests
	@Query("select p from Member m join m.enrolments e join e.brotherhood b join b.processions p where p.finalMode=1 and e.dropOutMoment=null and m.id=?1 and p not in (select p from Member m join m.requests r join r.procession p where r.status!='2' and m.id=?1)")
	Collection<Procession> processionsForRequestByMember(int varId);

}
