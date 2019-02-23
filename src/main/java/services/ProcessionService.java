
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

import repositories.ProcessionRepository;
import domain.Brotherhood;
import domain.Float;
import domain.Procession;
import domain.Request;

@Service
@Transactional
public class ProcessionService {

	//Managed repository

	@Autowired
	private ProcessionRepository	processionRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RequestService			requestService;

	@Autowired
	private FloatService			floatService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	//Simple CRUD methods

	public Procession create() {
		final Procession p = new Procession();

		p.setTicker(this.generateTicker());

		final Brotherhood b = (Brotherhood) this.actorService.findByPrincipal();
		p.setBrotherhood(b);
		p.setFinalMode(false);
		p.setRequests(new ArrayList<Request>());
		p.setFloats(new ArrayList<Float>());

		return p;
	}
	public Procession findOne(final int id) {
		Assert.notNull(id);

		return this.processionRepository.findOne(id);
	}

	public Collection<Procession> findAll() {
		return this.processionRepository.findAll();
	}

	public Procession save(final Procession p, final boolean b) {
		Assert.notNull(p);

		//Assertion to make sure that the procession is not on final mode.
		Assert.isTrue(p.getFinalMode() == false);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		if (b == true)
			p.setFinalMode(true);

		//TODO método checkSpam
		final Procession saved = this.processionRepository.save(p);
		//		this.actorService.checkSpam(saved.getAddress());
		//		this.actorService.checkSpam(saved.getDescription());

		return saved;
	}
	public void delete(final Procession p) {
		Assert.notNull(p);

		//A procession cannot be deleted if it's in final mode.
		//Assert.isTrue(p.getFinalMode() == false);

		//A procession cannot be deleted if it has any float.
		//Assert.isTrue(p.getFloats().isEmpty());

		//A procession cannot be deleted if it has any request.
		//Assert.isTrue(p.getRequests().isEmpty());

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getBrotherhood().getId());

		//		//Also, we delete the report related with the deleted complaint
		//		if (!(p.getRequests().isEmpty()))
		//			for (final Request req : p.getRequests())
		//				this.requestService.delete(req);

		final Brotherhood b = p.getBrotherhood();
		final Collection<Procession> processions = b.getProcessions();
		final Collection<Float> floats = p.getFloats();
		final Collection<Request> requests = p.getRequests();

		processions.remove(p);

		b.setProcessions(processions);
		this.brotherhoodService.save(b);

		if (!(p.getFloats().isEmpty()))
			for (final Float f : floats)
				this.floatService.delete(f);
		if (!(p.getRequests().isEmpty()))
			for (final Request req : requests)
				this.requestService.delete(req);

		this.processionRepository.delete(p);
	}
	//Other methods

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

	//The processions that are going to be organised in 30 days or less.
	public Collection<Procession> processionsBefore30Days() {
		return this.processionRepository.processionsBefore30Days();
	}

	//Processions by area
	public Collection<Procession> processionsByArea(final int id) {
		return this.processionRepository.processionsByArea(id);
	}
	//Processions with final mode = true
	public Collection<Procession> getFinalProcessions() {
		return this.processionRepository.getFinalProcessions();
	}

	//Listing of processions with finalMode = true that belong to a certain brotherhood.
	public Collection<Procession> finalProcessionsForBrotherhood(final int varId) {
		return this.processionRepository.finalProcessionsForBrotherhood(varId);
	}

}
