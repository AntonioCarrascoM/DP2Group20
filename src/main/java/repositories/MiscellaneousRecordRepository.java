
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	//The miscellaneous records from a  brotherhood
	@Query("select m from MiscellaneousRecord m where m.brotherhood.id = ?1")
	Collection<MiscellaneousRecord> miscellaneousRecordsfromBrotherhood(int id);

}
