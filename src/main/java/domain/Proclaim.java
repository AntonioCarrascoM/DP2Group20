
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Proclaim extends DomainEntity {

	//Attributes

	private Date	publicationMoment;
	private String	description;

	//Relationships

	private Chapter	chapter;


	//Getters

	@NotBlank
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Chapter getChapter() {
		return this.chapter;
	}

	//Setters

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setChapter(final Chapter chapter) {
		this.chapter = chapter;
	}

}
