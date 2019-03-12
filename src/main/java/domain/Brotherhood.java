
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Brotherhood extends Actor {

	//Attributes

	private String					title;
	private Date					establishmentDate;
	private String					pictures;

	//Relationships

	private Area					area;
	private Collection<Parade>		parades;
	private Collection<Enrolment>	enrolments;
	private Collection<Float>		floats;


	//Getters 

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}
	public String getPictures() {
		return this.pictures;
	}

	@Valid
	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Parade> getParades() {
		return this.parades;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Enrolment> getEnrolments() {
		return this.enrolments;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "brotherhood")
	public Collection<Float> getFloats() {
		return this.floats;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	public void setParades(final Collection<Parade> parades) {
		this.parades = parades;
	}

	public void setEnrolments(final Collection<Enrolment> enrolments) {
		this.enrolments = enrolments;
	}

	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;

	}
}
