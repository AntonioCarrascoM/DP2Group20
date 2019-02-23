
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RequestRepository;
import domain.Member;
import domain.Procession;
import domain.Request;
import domain.Status;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository	requestRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ProcessionService	processionService;


	//Supporting services --------------------------------

	//Simple CRUD methods

	public Request create(final int varId) {

		final Request request = new Request();

		request.setStatus(Status.PENDING);

		final Procession procession = this.processionService.findOne(varId);
		request.setProcession(procession);
		final Member m = (Member) this.actorService.findByPrincipal();
		request.setMember(m);
		request.setReason("There is no reason yet / No existe un motivo todavía");
		return request;
	}

	public Collection<Request> findAll() {
		return this.requestRepository.findAll();
	}

	public Request findOne(final int id) {
		Assert.notNull(id);

		return this.requestRepository.findOne(id);
	}

	public Request save(final Request request) {
		Assert.notNull(request);

		//TODO hay que hacer todo lo del estado y demás

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == request.getMember().getId());

		final Request saved = this.requestRepository.save(request);

		return saved;
	}
	public void delete(final Request request) {
		Assert.notNull(request);

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == request.getMember().getId());

		this.requestRepository.delete(request);
	}

	//Other methods

	//The ratio of requests to march in a procession, grouped by their status.

	public Double[] ratioRequestsByStatus(final int id) {
		return this.requestRepository.ratioRequestsByStatus(id);
	}

	//The ratio of requests to march grouped by status
	public Collection<Double> ratioRequestsByStatus() {
		return this.requestRepository.ratioRequestsByStatus();
	}
}
