
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	//The ratio of active sponsorships.
	@Query("select count(s)*1./(select count(s1)*1. from Sponsorship s1) from Sponsorship s where s.isActive='1'")
	Double ratioOfActiveSponsorships();

	//The sponsorhips given an sponsor id
	@Query("select sh from Sponsorship sh where sh.sponsor.id=?1")
	Collection<Sponsorship> sponsorshipsFromSponsor(int sponsorId);

	//disableSponsorshipsWithExpiredCreditCards
	@Query("select s from Sponsorship s join s.creditCard c where c.expYear<?1 or (c.expYear=?1 and c.expMonth<=?2)")
	Collection<Sponsorship> sponsorshipsWithExpiredCreditCards(int year, int month);

}
