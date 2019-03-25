
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
	@Query("select avg((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.), min((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.), max((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.), stddev((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.) from Chapter c join c.area a")
	Double[] avgMinMaxStddevParadesCoordinatedByChapter();

	//The chapters that co-ordinate at least 10% more parades than the average
	@Query("select c1.name from Chapter c1 join c1.area a1 where ((select count(p) from Parade p join p.brotherhood b where b.area.id=a1.id)*1.) > (select avg((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.1) from Chapter c join c.area a)")
	Collection<String> chaptersWith10PerCentParadesCoordinateThanAvg();

	//Retrieves the chapter associated with a certain area.
	@Query("select c from Chapter c where c.area.id=?1")
	Chapter getChapterForArea(int id);

}

//select c1.name from Chapter c1 join c1.area a1 where ((select count(p) from Parade p join p.brotherhood b where b.area.id=a1.id)*1.) > (select avg((select count(p) from Parade p join p.brotherhood b where b.area.id=a.id)*1.1) from Chapter c join c.area a);