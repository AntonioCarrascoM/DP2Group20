
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "dropOutMoment, brotherhoodToMember")
})
public class Enrolment extends DomainEntity {

	//Attributes

	private Date		moment;
	private Boolean		brotherhoodToMember;
	private Date		dropOutMoment;

	//Relationships

	private Position	position;
	private Brotherhood	brotherhood;
	private Member		member;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@Valid
	@ManyToOne(optional = true)
	public Position getPosition() {
		return this.position;
	}

	@NotNull
	public Boolean getBrotherhoodToMember() {
		return this.brotherhoodToMember;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDropOutMoment() {
		return this.dropOutMoment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	//Setters

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	public void setBrotherhoodToMember(final Boolean brotherhoodToMember) {
		this.brotherhoodToMember = brotherhoodToMember;
	}

	public void setDropOutMoment(final Date dropOutMoment) {
		this.dropOutMoment = dropOutMoment;
	}

}
