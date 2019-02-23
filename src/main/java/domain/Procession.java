
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Procession extends DomainEntity {

	//Attributes

	private String				ticker;
	private String				title;
	private String				description;
	private Date				moment;
	private Boolean				finalMode;

	//Relationships

	private Brotherhood			brotherhood;
	private Collection<Float>	floats;
	private Collection<Request>	requests;


	//Getters
	//TODO implementar el método del ticker (usar el método que corregía el bucle infinito)
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\d{2}\\d{2}\\d{2}-\\w{5}")
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Float> getFloats() {
		return this.floats;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "procession")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	//Setters

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}
}
