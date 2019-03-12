
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

	private String				title;
	private String				description;
	private String				pictures;

	//Relationships

	private Brotherhood			brotherhood;
	private Collection<Parade>	parades;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

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
	public Collection<Parade> getParades() {
		return this.parades;
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
	public void setParades(final Collection<Parade> parades) {
		this.parades = parades;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

}
