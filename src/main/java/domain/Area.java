
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "name")
})
public class Area extends DomainEntity {

	//Attributes

	private String					name;
	private String					pictures;

	//Relationships

	private Collection<Brotherhood>	brotherhoods;


	//Getters

	@NotBlank
	public String getName() {
		return this.name;
	}

	@NotBlank
	public String getPictures() {
		return this.pictures;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "area")
	public Collection<Brotherhood> getBrotherhoods() {
		return this.brotherhoods;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setPictures(final String pictures) {
		this.pictures = pictures;
	}

	public void setBrotherhoods(final Collection<Brotherhood> brotherhoods) {
		this.brotherhoods = brotherhoods;
	}

}
