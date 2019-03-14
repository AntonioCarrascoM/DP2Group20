
package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class FormObjectChapter {

	private String	name;
	private String	middleName;
	private String	surname;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;
	private String	title;
	private String	username;
	private String	password;
	private String	secondPassword;
	private Boolean	acceptedTerms;


	//Getters 

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

	public String getPhone() {
		return this.phone;
	}

	public String getAddress() {
		return this.address;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	@Size(min = 5, max = 32)
	public String getSecondPassword() {
		return this.secondPassword;
	}

	@NotNull
	public Boolean getAcceptedTerms() {
		return this.acceptedTerms;
	}

	//Setters 

	public void setName(final String name) {
		this.name = name;
	}
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
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
	public void setUsername(final String username) {
		this.username = username;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	public void setSecondPassword(final String secondPassword) {
		this.secondPassword = secondPassword;
	}
	public void setAcceptedTerms(final Boolean acceptedTerms) {
		this.acceptedTerms = acceptedTerms;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
}
