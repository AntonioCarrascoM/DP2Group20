
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Finds the messages of a certain box.
	@Query("select b.messages from Box b where b.id=?1")
	Collection<Message> messagesFromBox(int id);

	//Listing of the messages sent by a certain actor.
	@Query("select m from Message m where m.sender.id=?1")
	Collection<Message> sentMessagesForActor(int id);
}
