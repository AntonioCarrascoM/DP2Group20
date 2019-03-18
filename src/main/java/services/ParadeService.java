
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import domain.Brotherhood;
import domain.Float;
import domain.Parade;
import domain.ParadeStatus;
import domain.Request;

@Service
@Transactional
public class ParadeService {

	//Managed repository

	@Autowired
	private ParadeRepository	paradeRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private RequestService		requestService;

	@Autowired
	private FloatService		floatService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private ChapterService		chapterService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Parade create() {
		final Parade p = new Parade();

		p.setTicker(this.generateTicker());

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		p.setBrotherhood(b);
		p.setFinalMode(false);
		p.setRejectionReason("There is no reason yet");
		p.setRequests(new ArrayList<Request>());
		p.setFloats(new ArrayList<Float>());

		return p;
	}
	public Parade findOne(final int id) {
		Assert.notNull(id);

		return this.paradeRepository.findOne(id);
	}

	public Collection<Parade> findAll() {
		return this.paradeRepository.findAll();
	}

	public Parade save(final Parade p, final boolean b) {
		Assert.notNull(p);
		final Date date = p.getMoment();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		Assert.notNull(p.getBrotherhood().getArea());
		//Assertion to make sure that the parade is not on final mode.
		Assert.isTrue(p.getFinalMode() == false);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		if (b == true) {
			p.setFinalMode(true);
			p.setParadeStatus(ParadeStatus.SUBMITTED);
		}

		final Parade saved = this.paradeRepository.save(p);

		//Sending notification to members
		if (saved.getFinalMode() == true)
			this.messageService.paradePublished(saved);

		return saved;
	}

	public Parade saveFromChapter(final Parade p) {

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.chapterService.getChapterForArea(p.getBrotherhood().getArea().getId()).getId() == this.actorService.findByPrincipal().getId());

		return this.paradeRepository.save(p);
	}

	public Parade saveAux(final Parade p) {
		Assert.notNull(p);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		final Parade saved = this.paradeRepository.save(p);

		return saved;
	}

	public void delete(final Parade p) {
		Assert.notNull(p);

		//A parade cannot be deleted if it's in final mode.
		Assert.isTrue(p.getFinalMode() == false);

		//A parade cannot be deleted if it has any float.
		Assert.isTrue(p.getFloats().isEmpty());

		//A parade cannot be deleted if it has any request.
		Assert.isTrue(p.getRequests().isEmpty());

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		final Brotherhood b = p.getBrotherhood();
		final Collection<Parade> parades = b.getParades();
		final Collection<Float> floats = p.getFloats();
		final Collection<Request> requests = p.getRequests();

		parades.remove(p);

		b.setParades(parades);
		this.brotherhoodService.save(b);

		if (!(floats.isEmpty()))
			for (final Float f : floats) {
				final Collection<Parade> floatParades = f.getParades();
				floatParades.remove(p);
				f.setParades(floatParades);
				this.floatService.save(f);
			}

		if (!(requests.isEmpty()))
			for (final Request req : requests)
				this.requestService.delete(req);
		this.paradeRepository.delete(p);
	}

	//Reconstruct

	public Parade reconstruct(final Parade p, final BindingResult binding) {
		Parade result;

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.paradeRepository.findOne(p.getId());
		result.setTitle(p.getTitle());
		result.setDescription(p.getDescription());
		result.setMaxColumn(p.getMaxColumn());
		result.setMaxRow(p.getMaxRow());
		result.setMoment(p.getMoment());
		result.setFinalMode(p.getFinalMode());

		this.validator.validate(result, binding);

		final Date date = result.getMoment();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		Assert.notNull(result.getBrotherhood().getArea());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getBrotherhood().getId());

		return result;

	}
	//Other methods

	public Parade copy(final Parade original) {
		final Parade nueva = original;

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == original.getBrotherhood().getId());

		nueva.setTicker(this.generateTicker());
		nueva.setParadeStatus(null);
		nueva.setRejectionReason(null);
		nueva.setFinalMode(false);

		return this.paradeRepository.save(nueva);
	}

	//Generates the first half of the unique tickers.
	private String generateNumber() {
		final Date date = new Date();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0].trim().substring(2, 4);
		final String month = campos[1].trim();
		final String day = campos[2].trim();

		final String res = year + month + day;
		return res;
	}

	//Generates the alpha-numeric part of the unique tickers.
	private String generateString() {
		final Random c = new Random();
		String randomString = "";
		int i = 0;
		final int longitud = 5;
		while (i < longitud) {
			randomString += ((char) ((char) c.nextInt(26) + 65)); //mayus
			i++;
		}
		return randomString;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker() {
		final String res = this.generateNumber() + "-" + this.generateString();
		return res;
	}

	//Other methods

	//The parades that are going to be organised in 30 days or less.
	public Collection<Parade> paradesBefore30Days() {
		return this.paradeRepository.paradesBefore30Days();
	}

	//Parades by area
	public Collection<Parade> paradesByArea(final int id) {
		return this.paradeRepository.paradesByArea(id);
	}
	//Parades with final mode = true
	public Collection<Parade> getFinalParades() {
		return this.paradeRepository.getFinalParades();
	}

	//Listing of parades with finalMode = true that belong to a certain brotherhood.
	public Collection<Parade> finalParadesForBrotherhood(final int varId) {
		return this.paradeRepository.finalParadesForBrotherhood(varId);
	}

	//Parades which a member can do requests
	public Collection<Parade> paradesForRequestByMember(final int varId) {
		Collection<Parade> parades = new ArrayList<>();
		parades = this.paradeRepository.paradesForRequestByMember(varId);
		if (parades == null)
			return new ArrayList<>();
		else
			return parades;
	}

	//The ratio of parades in draft mode versus parades in final mode
	public Double ratioParadesInDraftModeVsFinalMode() {
		return this.paradeRepository.ratioParadesInDraftModeVsFinalMode();
	}

	//The ratio of parades in final mode grouped by status.
	public Double[] ratioParadesInFinalModeGroupByStatus() {
		return this.paradeRepository.ratioParadesInFinalModeGroupByStatus();
	}
}
