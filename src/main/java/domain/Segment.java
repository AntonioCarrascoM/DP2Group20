
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	//Attributes

	private Double	originCoordX;
	private Double	originCoordY;
	private Double	destinationCoordX;
	private Double	destinationCoordY;
	private Date	originDate;
	private Date	destinationDate;

	//Relationships
	private Parade	parade;


	//Getters

	//TODO Hacer que la origin coord de un segmento sea la destination coord del anterior segmento de esa parade
	@NotNull
	@Min(0)
	public Double getOriginCoordX() {
		return this.originCoordX;
	}
	@NotNull
	@Min(0)
	public Double getOriginCoordY() {
		return this.originCoordY;
	}
	@NotNull
	@Min(0)
	public Double getDestinationCoordX() {
		return this.destinationCoordX;
	}
	@NotNull
	@Min(0)
	public Double getDestinationCoordY() {
		return this.destinationCoordY;
	}
	//TODO Hacer que la origin date de un segmento sea la destination date del anterior segmento de esa parade
	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
	public Date getOriginDate() {
		return this.originDate;
	}

	@NotNull
	@Temporal(TemporalType.TIME)
	@DateTimeFormat(pattern = "HH:mm")
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

	public void setOriginCoordX(final Double originCoordX) {
		this.originCoordX = originCoordX;
	}
	public void setOriginCoordY(final Double originCoordY) {
		this.originCoordY = originCoordY;
	}

	public void setDestinationCoordX(final Double destinationCoordX) {
		this.destinationCoordX = destinationCoordX;
	}
	public void setDestinationCoordY(final Double destinationCoordY) {
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
