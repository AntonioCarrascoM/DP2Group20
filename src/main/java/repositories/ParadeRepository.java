
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	//The parades that are going to be organised in 30 days or less.
	@Query("select p from Parade p where p.moment-(CURRENT_TIMESTAMP+1) < 99000000")
	Collection<Parade> paradesBefore30Days();

	//Parades by area
	@Query("select p from Parade p join p.brotherhood b join b.area a where a.id=?1")
	Collection<Parade> paradesByArea(int id);

	//Listing of the parades with finalMode = true
	@Query("select p from Parade p where p.finalMode = true")
	Collection<Parade> getFinalParades();

	//Listing of parades with finalMode = true that belong to a certain brotherhood.
	@Query("select p from Parade p where p.finalMode=1 and p.brotherhood.id=?1")
	Collection<Parade> finalParadesForBrotherhood(int varId);

	//Parades which a member can do requests
	@Query("select p from Member m join m.enrolments e join e.brotherhood b join b.parades p where p.finalMode=1 and e.dropOutMoment=null and m.id=?1 and p not in (select p from Member m join m.requests r join r.parade p where r.status!='2' and m.id=?1)")
	Collection<Parade> paradesForRequestByMember(int varId);

	//The ratio of parades in draft mode versus parades in final mode
	@Query("select count(p0)*1./(select count(p1)*1. from Parade p1 where p1.finalMode='1') from Parade p0 where p0.finalMode='0'")
	Double ratioParadesInDraftModeVsFinalMode();

	//The ratio of parades in final mode grouped by status.
	@Query("select count(p)*1./(select count(p1) from Parade p1 where p1.finalMode='1') from Parade p where p.finalMode='1' group by p.paradeStatus")
	Collection<Double> ratioParadesInFinalModeGroupByStatus();

	//Parades with status accpeted
	@Query("select p from Parade p where p.paradeStatus = 1")
	Collection<Parade> paradesAccepted();

	//Parades with final mode for an area
	@Query("select p from Parade p join p.brotherhood b join b.area a where p.finalMode='1' and a.id=?1")
	Collection<Parade> finalParadesByArea(int id);
}
