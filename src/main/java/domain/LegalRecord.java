
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends Record {

	//Attributes
	private String	legalName;
	private Integer	vatNumber;
	private String	applicableLaws;


	//Getters

	@NotBlank
	public String getLegalName() {
		return this.legalName;
	}

	@Min(0)
	public Integer getvatNumber() {
		return this.vatNumber;
	}

	@NotBlank
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	//Setters

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	public void setEndYear(final Integer vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setPhotos(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

}
