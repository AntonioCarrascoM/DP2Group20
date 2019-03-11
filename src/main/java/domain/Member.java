
package domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
public class Member extends Actor {

	//Relationships

	private Finder					finder;
	private Collection<Enrolment>	enrolments;
	private Collection<Request>		requests;


	//Getters 

	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "member")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "member")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	//Setters

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}
}
