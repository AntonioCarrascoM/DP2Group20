
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Chapter extends Actor {

	//Attributes

	private String	title;

	//Relationships

	private Area	area;


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

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

}
