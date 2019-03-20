
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {

	//Returns the segments of a parade
	@Query("select s from Segment s where s.parade.id =?1")
	Collection<Segment> getSegmentsForParade(int paradeId);

	//Returns the segments of a parade ordered by destination date, the last one is the first in the collection
	@Query("select s from Segment s where s.parade.id =?1 order by s.destinationDate desc")
	Collection<Segment> getOrderedSegmentsForParade(int paradeId);

}
