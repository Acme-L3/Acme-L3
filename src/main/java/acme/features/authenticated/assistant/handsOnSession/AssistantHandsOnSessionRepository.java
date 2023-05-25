
package acme.features.authenticated.assistant.handsOnSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantHandsOnSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select ts from HandsOnSession ts where ts.tutorial.id = :tutorialId")
	Collection<HandsOnSession> findHandsOnSessionsByTutorialId(int tutorialId);

	@Query("select ts.tutorial from HandsOnSession ts where ts.id = :handsOnSessionId")
	Tutorial findTutorialByHandsOnSessionId(int handsOnSessionId);

	@Query("select ts from HandsOnSession ts where ts.id = :handsOnSessionId")
	HandsOnSession findHandsOnSessionById(int handsOnSessionId);

}
