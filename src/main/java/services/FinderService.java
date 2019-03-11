
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.Member;
import domain.Procession;

@Service
@Transactional
public class FinderService {

	//Managed service

	@Autowired
	private FinderRepository		finderRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private ProcessionService		processionService;


	//Simple CRUD methods --------------------------------

	public Finder create() {
		final Finder f = new Finder();
		f.setProcessions(new ArrayList<Procession>());
		return f;
	}

	public Finder findOne(final int id) {
		Assert.notNull(id);
		return this.finderRepository.findOne(id);
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder save(final Finder f) {
		Assert.notNull(f);
		//Assertion that the user modifying this finder has the correct privilege.
		Assert.isTrue(f.getId() == this.findPrincipalFinder().getId());//this.findPrincipalFinder().getId()
		//If all fields of the finder are null, the finder returns the entire listing of available tasks.
		f.setMoment(new Date(System.currentTimeMillis() - 1));
		final Finder saved = this.finderRepository.save(f);

		return saved;
	}

	public void delete(final Finder f) {
		Assert.notNull(f);

		//Assertion that the user deleting this finder has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == this.memberService.memberByFinder(f.getId()).getId());

		this.finderRepository.delete(f);
	}

	public Finder findPrincipalFinder() {
		final Actor a = this.actorService.findByPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(auth));

		final Member m = (Member) this.actorService.findOne(a.getId());
		Finder fd = new Finder();
		if (m.getFinder() == null) {
			fd = this.create();
			final Finder saved = this.finderRepository.save(fd);
			m.setFinder(saved);
			this.memberService.save(m);
			return saved;
		} else
			return m.getFinder();
	}

	public Collection<Procession> find(final Finder finder) {
		Assert.notNull(finder);
		Collection<Procession> processions = new ArrayList<>();
		Collection<Procession> results = new ArrayList<>();
		String keyWord = finder.getKeyWord();
		Date minimumDate = finder.getMinimumDate();
		Date maximumDate = finder.getMaximumDate();

		if (finder.getKeyWord() == null)
			keyWord = "";
		if (finder.getMinimumDate() == null)
			minimumDate = new Date(631152000L);
		if (finder.getMaximumDate() == null)
			maximumDate = new Date(2524694400000L);

		final Collection<Procession> firstResults = this.findProcessions(keyWord, minimumDate, maximumDate);
		Collection<Procession> secondResults = new ArrayList<>();

		if (finder.getArea() != null)
			secondResults = this.processionService.processionsByArea(finder.getArea().getId());
		else
			secondResults = this.processionService.findAll();

		processions = this.intersection(firstResults, secondResults);
		results = this.limitResults(processions);
		return results;
	}

	public Collection<Procession> limitResults(final Collection<Procession> processions) {
		Collection<Procession> results = new ArrayList<>();
		final int maxResults = this.configurationService.findAll().iterator().next().getMaxFinderResults();
		if (processions.size() > maxResults)
			results = new ArrayList<Procession>(((ArrayList<Procession>) processions).subList(0, maxResults));
		else
			results = processions;
		return results;
	}

	private Collection<Procession> intersection(final Collection<Procession> a, final Collection<Procession> b) {
		final Collection<Procession> c = new ArrayList<>();
		final Collection<Procession> mayor = a.size() > b.size() ? a : b;
		for (final Procession f : mayor)
			if (a.contains(f) && b.contains(f))
				c.add(f);
		return c;
	}

	//Search processions 
	public Collection<Procession> findProcessions(final String keyWord, final Date minimunDate, final Date maximunDate) {
		return this.finderRepository.findProcessions(keyWord, minimunDate, maximunDate);
	}

	//The minimum, the maximum, the average, and the standard deviation of the number of results in the finders.
	public Double[] minMaxAvgStddevResultsFinders() {
		return this.finderRepository.minMaxAvgAndStddevOfResultsByFinder();
	}

	//  The ratio of empty versus non-empty finders
	public Double ratioEmptyVersusNonEmptyFinders() {
		Double res = this.finderRepository.ratioEmptyVsNonEmptyFinders();
		if (res == null)
			res = 0.0;
		return res;
	}
}
