
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	//Attributes

	private String	nameEN;
	private String	nameES;


	//Getter

	@NotNull
	public String getNameEN() {
		return this.nameEN;
	}

	@NotNull
	public String getNameES() {
		return this.nameES;
	}

	//Setter

	public void setNameEN(final String nameEN) {
		this.nameEN = nameEN;
	}

	public void setNameES(final String nameES) {
		this.nameES = nameES;
	}

}
