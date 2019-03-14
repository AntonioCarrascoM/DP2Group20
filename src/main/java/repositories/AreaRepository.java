
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	//The ratio of brotherhoods per area
	@Query("select distinct a.brotherhoods.size*1./(select count(b) from Brotherhood b) from Brotherhood b1 join b1.area a")
	Collection<Double> ratioOfBrotherhoodsByArea();

	//The count of brotherhoods per area
	@Query("select a.brotherhoods.size from Area a")
	Collection<Integer> countOfBrotherhoodsByArea();

	//The minimum, the maximum, the average, and the
	//standard deviation of the number of brotherhoods per area
	@Query("select min(a.brotherhoods.size), max(a.brotherhoods.size), avg(a.brotherhoods.size), stddev(a.brotherhoods.size) from Area a")
	Double[] minMaxAvgAndStddevOfBrotherhoodsByArea();

	//The ratio of areas that are not co-ordinated by any chapters
	@Query("select count(a)*1./(select count(a1) from Area a1) from Area a where a not in (select c.area from Chapter c)")
	Collection<Area> areasNotCoordinated();

}
