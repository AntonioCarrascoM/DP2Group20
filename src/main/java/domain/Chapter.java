
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Chapter extends Actor {

	//Attributes

	private String					title;

	//Relationships

	private Area					area;
	private Collection<Proclaim>	proclaims;


	//Getters

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Valid
	@OneToOne(optional = true)
	public Area getArea() {
		return this.area;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "chapter")
	public Collection<Proclaim> getProclaims() {
		return this.proclaims;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	public void setProclaims(final Collection<Proclaim> proclaims) {
		this.proclaims = proclaims;
	}

}
