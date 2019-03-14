
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ProclaimRepository;
import domain.Chapter;
import domain.Proclaim;

@Service
@Transactional
public class ProclaimService {

	//Managed repository

	@Autowired
	private ProclaimRepository	proclaimRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ChapterService		chapterService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Proclaim create() {
		final Proclaim p = new Proclaim();

		final Chapter ch = (Chapter) this.actorService.findByPrincipal();
		p.setChapter(ch);

		return p;
	}
	public Proclaim findOne(final int id) {
		Assert.notNull(id);

		return this.proclaimRepository.findOne(id);
	}

	public Collection<Proclaim> findAll() {
		return this.proclaimRepository.findAll();
	}

	public Proclaim save(final Proclaim p) {
		Assert.notNull(p);
		final Date date = p.getPublicationMoment();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		Assert.notNull(p.getChapter().getArea());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getChapter().getId());

		final Proclaim saved = this.proclaimRepository.save(p);

		return saved;
	}

	public Proclaim saveAux(final Proclaim p) {
		Assert.notNull(p);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getChapter().getId());

		final Proclaim saved = this.proclaimRepository.save(p);

		return saved;
	}

	public void delete(final Proclaim p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getChapter().getId());

		final Chapter ch = p.getChapter();
		final Collection<Proclaim> proclaims = ch.getProclaims();

		proclaims.remove(p);

		ch.setProclaims(proclaims);
		this.chapterService.save(ch);

		this.proclaimRepository.delete(p);
	}

	//Reconstruct

	public Proclaim reconstruct(final Proclaim p, final BindingResult binding) {
		Proclaim result;

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.proclaimRepository.findOne(p.getId());
		result.setPublicationMoment(p.getPublicationMoment());
		result.setDescription(p.getDescription());

		this.validator.validate(result, binding);

		final Date date = result.getPublicationMoment();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		Assert.notNull(result.getChapter().getArea());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getChapter().getId());

		return result;

	}
	//Other methods

	//Other methods

}
