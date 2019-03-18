
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.CreditCard;
import domain.Parade;
import domain.Sponsor;

public class FormObjectSponsorship {

	private String		banner;
	private String		targetURL;
	private CreditCard	creditCard;
	private Boolean		isActive;
	private Sponsor		sponsor;
	private Parade		parade;
	private Double		charge;


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
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	@NotNull
	public Boolean getIsActive() {
		return this.isActive;
	}

	@NotNull
	public Parade getParade() {
		return this.parade;
	}

	@NotNull
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	@NotNull
	public Double getCharge() {
		return this.charge;
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

	public void setCharge(final Double charge) {
		this.charge = charge;
	}
}
