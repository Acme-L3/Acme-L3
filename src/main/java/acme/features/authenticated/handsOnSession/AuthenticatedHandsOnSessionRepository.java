
package acme.features.authenticated.handsOnSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedHandsOnSessionRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select hs from HandsOnSession hs where hs.tutorial.id = :tutorialId")
	Collection<HandsOnSession> findHandsOnSessionByTutorialId(int tutorialId);

	@Query("select hs.tutorial from HandsOnSession hs where hs.id = :handsOnSessionId")
	Tutorial findTutorialByHandsOnSessionId(int handsOnSessionId);

	@Query("select hs from HandsOnSession hs where hs.id = :handsOnSessionId")
	HandsOnSession findHandsOnSessionById(int handsOnSessionId);

}
