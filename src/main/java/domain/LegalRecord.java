
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends Record {

	//Attributes
	private String	legalName;
	private Double	vatNumber;
	private String	applicableLaws;


	//Getters

	@NotBlank
	public String getLegalName() {
		return this.legalName;
	}

	@NotNull
	@Min(0)
	public Double getVatNumber() {
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

	public void setVatNumber(final Double vatNumber) {
		this.vatNumber = vatNumber;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

}
