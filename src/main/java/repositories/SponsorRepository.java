
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	//The average, the minimum, the maximum, and the standard deviation of active sponsorships per sponsor
	@Query("select avg((select count(s) from Sponsorship s where s.isActive='1' and s.sponsor.id=x.id)*1.), min((select count(s) from Sponsorship s where s.isActive='1' and s.sponsor.id=x.id)*1.), max((select count(s) from Sponsorship s where s.isActive='1' and s.sponsor.id=x.id)*1.), stddev((select count(s) from Sponsorship s where s.isActive='1' and s.sponsor.id=x.id)*1.) from Sponsor x")
	Double[] avgMinMaxAndStddevOfActiveSponsorshipsPerSponsor();

	//The top-5 sponsors in terms of number of active sponsorships.
	@Query("select x1 from Sponsor x1 order by (select count(s) from Sponsorship s where s.isActive='1' and s.sponsor.id=x1.id)*1. desc")
	Collection<Sponsor> top5SponsorsByActiveSponsorships();

}
