
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "`Float`")
public class Float extends DomainEntity {

	//Attributes

	private String					title;
	private String					description;
	private String					pictures;

	//Relationships

	private Brotherhood				brotherhood;
	private Collection<Procession>	processions;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	//TODO método checkPictures
	public String getPictures() {
		return this.pictures;
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
	public Collection<Procession> getProcessions() {
		return this.processions;
	}

	//Setters

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}
	public void setProcessions(final Collection<Procession> processions) {
		this.processions = processions;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
