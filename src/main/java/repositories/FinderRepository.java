
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Procession;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//Search fixUpTask 
	@Query("select p from Procession p where (p.ticker like %?1% or  p.title like %?1% or p.description like %?1%) and (p.moment between ?2 and ?3))")
	Collection<Procession> findProcessions(String keyWord, Date minimunDate, Date maximunDate);

	//The minimum, the maximum, the average, and the standard deviation of the number of results in the finders.
	@Query("select min(f.processions.size), avg(f.processions.size), stddev(f.processions.size) from Finder f")
	Double[] minMaxAvgAndStddevOfResultsByFinder();

	//The ratio of empty versus non-empty finders
	@Query("select count(f)/(select count(f1) from Finder f1 where f1.processions.size>0) from Finder f where f.processions.size=0")
	Double ratioEmptyVsNonEmptyFinders();

}
