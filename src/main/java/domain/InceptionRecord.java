
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class InceptionRecord extends Record {

	//Attributes
	private String	photos;


	//Getters

	@NotBlank
	public String getPhotos() {
		return this.photos;
	}

	//Setters
	public void setPhotos(final String photos) {
		this.photos = photos;
	}

}
