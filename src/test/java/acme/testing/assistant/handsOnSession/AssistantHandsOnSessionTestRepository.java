
package acme.testing.assistant.handsOnSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantHandsOnSessionTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssistantUsername(String username);

	@Query("select hs from HandsOnSession hs where hs.tutorial.assistant.userAccount.username = :username")
	Collection<HandsOnSession> findManyHandsOnSessionsByAssistantUsername(String username);

}
