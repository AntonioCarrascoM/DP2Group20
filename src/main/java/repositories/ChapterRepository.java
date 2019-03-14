
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

	//Return then chapters with 10% more applications approved than average
	@Query("select ch from Chapter ch join ch.parades p where a.status='1' group by hw having ch.parades.size >= " + "(select avg(ch.parades.size)*1.1 from Chapter ch) ")
	Collection<Chapter> chapterWithMoreParadesApprovedThanAvg();

}
