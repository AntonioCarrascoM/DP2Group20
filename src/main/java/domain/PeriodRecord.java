
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends Record {

	//Attributes
	private Integer	startYear;
	private Integer	endYear;
	private String	photos;


	//Getters

	@Min(0)
	public Integer getStartYear() {
		return this.startYear;
	}

	@Min(0)
	public Integer getEndYear() {
		return this.endYear;
	}

	@NotBlank
	public String getPhotos() {
		return this.photos;
	}

	//Setters

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	public void setPhotos(final String photos) {
		this.photos = photos;
	}
}
