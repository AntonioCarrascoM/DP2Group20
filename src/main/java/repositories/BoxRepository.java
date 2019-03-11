
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	//Find a box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2")
	Box getBoxByName(int id, String name);

	//Find a system box in an actor's collection given the box's name.
	@Query("select b from Box b where b.actor.id = ?1 and b.name = ?2 and b.system = true")
	Box getSystemBoxByName(int id, String name);

	//Find a box in an actor's collection given a certain message in that box.
	@Query("select b from Box b where b.actor.id=?1 and ?2 member of b.messages")
	Collection<Box> getBoxesByMessageAndActor(int actorId, int messageId);
}
