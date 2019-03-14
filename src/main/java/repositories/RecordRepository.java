
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Record;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

	//The average, the minimum, the maximum, and the standard deviation of the
	//number of records per history
	@Query("select avg((select count(r) from Record r where r.brotherhood.id=b.id)*1.0), min((select count(r) from Record r where r.brotherhood.id=b.id)*1.0), max((select count(r) from Record r where r.brotherhood.id=b.id)*1.0), stddev((select count(r) from Record r where r.brotherhood.id=b.id)*1.0) from Brotherhood b")
	Double[] avgMinMaxStddevRecordsForHistory();

}
