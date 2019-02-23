
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	//Attributes

	private String						name;
	private String						middleName;
	private String						surname;
	private String						photo;
	private String						email;
	private String						phone;
	private String						address;
	private Double						score;
	private boolean						spammer;

	//Relationships

	private UserAccount					userAccount;
	private Collection<SocialProfile>	socialProfiles;
	private Collection<Box>				boxes;


	//Getter

	@NotBlank
	public String getName() {
		return this.name;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	@NotBlank
	public String getEmail() {
		return this.email;
	}
	//TODO Implementar el checkPhone del acmeHW
	public String getPhone() {
		return this.phone;
	}
	//TODO Implementar el checkAddress del acmeHW
	public String getAddress() {
		return this.address;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "actor")
	public Collection<Box> getBoxes() {
		return this.boxes;
	}

	@Range(min = -1, max = 1)
	public Double getScore() {
		return this.score;
	}

	public boolean isSpammer() {
		return this.spammer;
	}

	//Setters

	public void setName(final String name) {
		this.name = name;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}

	public void setBoxes(final Collection<Box> boxes) {
		this.boxes = boxes;
	}

	public void setSpammer(final boolean spammer) {
		this.spammer = spammer;
	}

	public void setScore(final Double score) {
		this.score = score;
	}

}
