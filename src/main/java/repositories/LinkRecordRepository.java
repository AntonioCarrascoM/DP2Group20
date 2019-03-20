
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LinkRecord;

@Repository
public interface LinkRecordRepository extends JpaRepository<LinkRecord, Integer> {

	//The link records from a  brotherhood
	@Query("select l from LinkRecord l where l.brotherhood.id = ?1")
	Collection<LinkRecord> linkRecordsfromBrotherhood(int id);
}
