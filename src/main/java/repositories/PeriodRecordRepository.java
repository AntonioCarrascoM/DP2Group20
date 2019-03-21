
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PeriodRecord;

@Repository
public interface PeriodRecordRepository extends JpaRepository<PeriodRecord, Integer> {

	//The legal records from a  brotherhood
	@Query("select p from PeriodRecord p where p.brotherhood.id = ?1")
	Collection<PeriodRecord> periodRecordsfromBrotherhood(int id);

}
