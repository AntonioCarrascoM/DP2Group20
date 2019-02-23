
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	//Attributes

	private String				name;
	private boolean				system;

	//Relationships

	private Actor				actor;
	private Box					parent;
	private Collection<Box>		children;
	private Collection<Message>	messages;


	//Getter

	@NotBlank
	public String getName() {
		return this.name;
	}

	public boolean isSystem() {
		return this.system;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	@ManyToOne(optional = true)
	public Box getParent() {
		return this.parent;
	}

	@Valid
	@OneToMany(mappedBy = "parent")
	public Collection<Box> getChildren() {
		return this.children;
	}

	@Valid
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	//Setter

	public void setName(final String name) {
		this.name = name;
	}

	public void setSystem(final boolean system) {
		this.system = system;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

	public void setParent(final Box parent) {
		this.parent = parent;
	}

	public void setChildren(final Collection<Box> children) {
		this.children = children;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}
}
