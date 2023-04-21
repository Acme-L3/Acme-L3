
package acme.features.authenticated.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :tutorialId")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int tutorialId);

	@Query("select ts.tutorial from TutorialSession ts where ts.id = :tutorialSessionId")
	Tutorial findTutorialByTutorialSessionId(int tutorialSessionId);

	@Query("select ts from TutorialSession ts where ts.id = :tutorialSessionId")
	TutorialSession findTutorialSessionById(int tutorialSessionId);
}
