
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

	//The ratio of requests to march in a procession, grouped by their status.
	@Query("select count(r)*1.0/(select count(s) from Request s where s.procession.id=?1) from Request r where r.procession.id=?1 group by r.status")
	Double[] ratioRequestsByStatus(int id);

	//The ratio of requests to march grouped by status
	@Query("select count(r)*1.0/(select count(s) from Request s) from Request r group by r.status")
	Collection<Double> ratioRequestsByStatus();

	//List of the approved requests for a certain procession.
	@Query("select distinct r from Procession p join p.requests r where r.status='1' and p.id=?1")
	Collection<Request> approvedRequestsForProcession(int id);

	//Returns a request for a certain column number, row number and procession.
	@Query("select r from Request r where r.customRow=?1 and r.customColumn=?2 and r.status='1' and r.procession.id=?3")
	Collection<Request> requestForRowColumnAndProcession(int row, int column, int pid);
}
