
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {

	//The ratio and the count of brotherhoods per area
	@Query("select count(b)*1./(select count(b1) from Brotherhood b1), count(b) from Area a join a.brotherhoods b where a.id=?1")
	Collection<Double> ratioAndCountOfBrotherhoodsByArea(int id);

	//The minimum, the maximum, the average, and the
	//standard deviation of the number of brotherhoods per area
	@Query("select min(a.brotherhoods.size), max(a.brotherhoods.size), avg(a.brotherhoods.size), stddev(a.brotherhoods.size) from Area a")
	Double[] minMaxAvgAndStddevOfBrotherhoodsByArea();
}
//select count(b)*1./(select count(b1) from Brotherhood b1) from Area a join a.brotherhoods b where a.id=683;