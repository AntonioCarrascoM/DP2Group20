
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.InceptionRecord;

@Repository
public interface InceptionRecordRepository extends JpaRepository<InceptionRecord, Integer> {

	//The inception records from a  brotherhood
	@Query("select i from InceptionRecord i where i.brotherhood.id = ?1")
	InceptionRecord inceptionRecordfromBrotherhood(int id);

}
