
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	//The average, the minimum, the maximum, and the standard deviation of the
	//number of parades co-ordinated by the chapters
	@Query("select avg(b.parades.size), min(b.parades.size), max(b.parades.size), stddev(b.parades.size) from Chapter c join c.area a join a.brotherhoods b")
	Double[] avgMinnMaxStddevParadesCoordinatedByChapter();

	//The chapters that co-ordinate at least 10% more parades than the average
	@Query("select c1 from Chapter c1 join c1.area a1 join a1.brotherhoods b1 where (b1.parades.size)*1.>(select avg(b.parades.size) from Chapter c join c.area a join a.brotherhoods b)*1.1")
	Collection<Chapter> chaptersWith10PerCentParadesCoordinateThanAvg();

}
