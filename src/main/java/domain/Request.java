
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "status")
})
public class Request extends DomainEntity {

	//Attributes

	private Status	status;
	private Integer	customRow;
	private Integer	customColumn;
	private String	reason;

	//Relationships

	private Member	member;
	private Parade	parade;


	//Getters

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	@Min(value = 0)
	public Integer getCustomRow() {
		return this.customRow;
	}

	@Min(value = 0)
	public Integer getCustomColumn() {
		return this.customColumn;
	}

	@NotBlank
	public String getReason() {
		return this.reason;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Member getMember() {
		return this.member;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Parade getParade() {
		return this.parade;
	}

	//Setters

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	public void setMember(final Member member) {
		this.member = member;
	}

	public void setCustomRow(final Integer customRow) {
		this.customRow = customRow;
	}

	public void setCustomColumn(final Integer customColumn) {
		this.customColumn = customColumn;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}
}
