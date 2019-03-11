
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Attributes

	private String					keyWord;
	private Date					minimumDate;
	private Date					maximumDate;
	private Date					moment;

	//Relationships

	private Area					area;
	private Collection<Procession>	processions;


	//Getters

	public String getKeyWord() {
		return this.keyWord;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMinimumDate() {
		return this.minimumDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMaximumDate() {
		return this.maximumDate;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	@Valid
	@ManyToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	//Setters

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public void setMinimumDate(final Date minimumDate) {
		this.minimumDate = minimumDate;
	}

	public void setMaximumDate(final Date maximumDate) {
		this.maximumDate = maximumDate;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

}
