
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import domain.Member;
import domain.Request;
import domain.Status;

@Service
@Transactional
public class RequestService {

	//Managed repository

	@Autowired
	private RequestRepository	requestRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private MessageService		messageService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Request create() {

		final Request request = new Request();

		request.setStatus(Status.PENDING);
		request.setCustomColumn(null);
		request.setCustomRow(null);

		final Member m = (Member) this.actorService.findByPrincipal();
		request.setMember(m);
		request.setReason("There is no reason yet / No existe un motivo todavia");
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

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == request.getMember().getId() || this.actorService.findByPrincipal().getId() == request.getParade().getBrotherhood().getId());

		//Assertion to make sure that the column and row numbers are less than the maximum allowed.
		if (request.getCustomColumn() != null && request.getCustomRow() != null)
			Assert.isTrue(request.getCustomColumn() <= request.getParade().getMaxColumn() && request.getCustomRow() <= request.getParade().getMaxRow());

		//Sending request status has changed notification
		if (this.actorService.findByPrincipal().getId() == request.getParade().getBrotherhood().getId())
			if ((request.getStatus() == Status.APPROVED || request.getStatus() == Status.REJECTED) && (request.getCustomRow() == null || request.getCustomColumn() == null))
				this.messageService.requestStatusNotification(request);

		//Calculating automatically the position for a request.
		if (request.getStatus() == Status.APPROVED && (request.getCustomRow() == null || request.getCustomColumn() == null))
			this.calculatePosition(request);

		//Assertion to make sure that the position is a valid one.
		if (request.getStatus() == Status.APPROVED)
			Assert.isTrue(this.checkPosition(request));

		final Request saved = this.requestRepository.save(request);

		return saved;
	}

	public void delete(final Request request) {
		Assert.notNull(request);

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == request.getMember().getId() || this.actorService.findByPrincipal().getId() == request.getParade().getBrotherhood().getId());

		this.requestRepository.delete(request);
	}

	//Other methods

	public Request calculatePosition(final Request r) {

		final Integer maxRow = r.getParade().getMaxRow();
		final Integer maxColumn = r.getParade().getMaxColumn();
		Boolean salir = false;
		int i = 0;
		int j = 0;
		for (i = 0; i < maxColumn; i++) {
			for (j = 0; j < maxRow; j++)
				if (this.requestForRowColumnAndParade(i, j, r.getParade().getId()).isEmpty()) {
					salir = true;
					r.setCustomColumn(i);
					r.setCustomRow(j);
					break;
				}
			if (salir == true)
				break;
		}
		return r;
	}

	public Boolean checkPosition(final Request r) {
		Boolean res = false;
		Collection<Request> reqs = new ArrayList<Request>();

		reqs = this.requestForRowColumnAndParade(r.getCustomRow(), r.getCustomColumn(), r.getParade().getId());

		if (reqs.isEmpty() || reqs.iterator().next() == r)
			res = true;

		return res;
	}

	//Reconstruct

	public Request reconstruct(final Request r, final BindingResult binding) {
		Request result;
		final Authority authBrotherhood = new Authority();
		authBrotherhood.setAuthority(Authority.BROTHERHOOD);

		final Authority authMember = new Authority();
		authMember.setAuthority(Authority.MEMBER);

		if (r.getId() == 0) {
			result = this.create();
			result.setParade(r.getParade());
		} else {
			result = this.requestRepository.findOne(r.getId());

			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authBrotherhood) && result.getStatus() != Status.APPROVED) {
				result.setStatus(r.getStatus());
				result.setReason(r.getReason());
			} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authBrotherhood) && result.getStatus() == Status.APPROVED) {
				result.setCustomColumn(r.getCustomColumn());
				result.setCustomRow(r.getCustomRow());
			}
		}

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this request has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getMember().getId() || this.actorService.findByPrincipal().getId() == result.getParade().getBrotherhood().getId());

		//Assertion to make sure that the column and row numbers are less than the maximum allowed.
		if (result.getCustomColumn() != null && result.getCustomRow() != null)
			Assert.isTrue(result.getCustomColumn() <= result.getParade().getMaxColumn() && result.getCustomRow() <= result.getParade().getMaxRow());

		//Calculating automatically the position for a request.
		if (result.getStatus() == Status.APPROVED && (result.getCustomRow() == null || result.getCustomColumn() == null))
			this.calculatePosition(result);

		//Assertion to make sure that the position is a valid one.
		if (result.getStatus() == Status.APPROVED)
			Assert.isTrue(this.checkPosition(result));

		return result;

	}
	//The ratio of requests to march in a parade, grouped by their status.

	public Double[] ratioRequestsByStatus(final int id) {
		return this.requestRepository.ratioRequestsByStatus(id);
	}

	//The ratio of requests to march grouped by status
	public Collection<Double> ratioRequestsByStatus() {
		return this.requestRepository.ratioRequestsByStatus();
	}

	//List of the approved requests for a certain parade.
	public Collection<Request> approvedRequestsForParade(final int id) {
		return this.requestRepository.approvedRequestsForParade(id);
	}

	//Returns a request for a certain column number, row number and parade.
	public Collection<Request> requestForRowColumnAndParade(final int row, final int column, final int pid) {
		return this.requestRepository.requestForRowColumnAndParade(row, column, pid);
	}
}
