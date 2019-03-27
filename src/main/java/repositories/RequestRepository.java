
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	//The ratio of requests to march in a parade, grouped by their status.
	@Query("select count(r)*1.0/(select count(s) from Request s where s.parade.id=?1) from Request r where r.parade.id=?1 group by r.status")
	Double[] ratioRequestsByStatus(int id);

	//The ratio of requests to march grouped by status
	@Query("select count(r)*1.0/(select count(s) from Request s) from Request r group by r.status")
	Collection<Double> ratioRequestsByStatus();

	//List of the approved requests for a certain parade.
	@Query("select distinct r from Parade p join p.requests r where r.status='1' and p.id=?1")
	Collection<Request> approvedRequestsForParade(int id);

	//Returns a request for a certain column number, row number and parade.
	@Query("select r from Request r where r.customRow=?1 and r.customColumn=?2 and r.status='1' and r.parade.id=?3")
	Collection<Request> requestForRowColumnAndParade(int row, int column, int pid);

	//List of the requests for a certain parade ordered by status.
	@Query("select r from Request r where r.parade.id=?1 order by r.status")
	Collection<Request> requestOrderByStatus(int id);
}
