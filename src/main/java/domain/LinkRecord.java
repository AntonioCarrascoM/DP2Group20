
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class LinkRecord extends Record {

	//Attributes
	private String	link;


	//Getters

	@URL
	public String getLink() {
		return this.link;
	}

	//Setters
	public void setLink(final String link) {
		this.link = link;
	}

}
