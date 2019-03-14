
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	//Attributes

	private String		banner;
	private String		targetURL;
	private CreditCard	creditCard;
	private Boolean		isActive;

	//Relationships

	private Sponsor		sponsor;
	private Parade		parade;


	//Getters

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	@NotBlank
	@URL
	public String getTargetURL() {
		return this.targetURL;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	@NotNull
	public Boolean getIsActive() {
		return this.isActive;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Parade getParade() {
		return this.parade;
	}

	//Setters

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public void setTargetURL(final String targetURL) {
		this.targetURL = targetURL;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public void setIsActive(final Boolean isActive) {
		this.isActive = isActive;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

}
