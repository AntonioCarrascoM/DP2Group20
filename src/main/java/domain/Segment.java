
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	//Attributes

	private String	originCoordX;
	private String	originCoordY;
	private String	destinationCoordX;
	private String	destinationCoordY;
	private Date	originDate;
	private Date	destinationDate;

	//Relationships
	private Parade	parade;


	//Getters

	@NotBlank
	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")
	public String getOriginCoordX() {
		return this.originCoordX;
	}
	@NotNull
	@Pattern(regexp = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
	public String getOriginCoordY() {
		return this.originCoordY;
	}
	@NotBlank
	@Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)$")
	public String getDestinationCoordX() {
		return this.destinationCoordX;
	}
	@NotNull
	@Pattern(regexp = "^[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
	public String getDestinationCoordY() {
		return this.destinationCoordY;
	}
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOriginDate() {
		return this.originDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDestinationDate() {
		return this.destinationDate;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Parade getParade() {
		return this.parade;
	}

	//Setters

	public void setOriginCoordX(final String originCoordX) {
		this.originCoordX = originCoordX;
	}
	public void setOriginCoordY(final String originCoordY) {
		this.originCoordY = originCoordY;
	}

	public void setDestinationCoordX(final String destinationCoordX) {
		this.destinationCoordX = destinationCoordX;
	}
	public void setDestinationCoordY(final String destinationCoordY) {
		this.destinationCoordY = destinationCoordY;
	}
	public void setOriginDate(final Date originDate) {
		this.originDate = originDate;
	}
	public void setDestinationDate(final Date destinationDate) {
		this.destinationDate = destinationDate;
	}
	public void setParade(final Parade parade) {
		this.parade = parade;
	}

}
