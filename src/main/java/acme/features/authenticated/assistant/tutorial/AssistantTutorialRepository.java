
package acme.features.authenticated.assistant.tutorial;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.course.Course;
import acme.entitites.session.HandsOnSession;
import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("select au from Assistant au where au.id = :id")
	Assistant findAssistantById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int id);

	@Query("select ts from HandsOnSession ts where ts.tutorial.id = :id")
	Collection<HandsOnSession> findHandsOnSessionsByTutorialId(int id);

	@Query("select ts.startDate from HandsOnSession ts where ts.tutorial.id = :id")
	Collection<Date> findHandsOnSessionsStartDateByTutorialId(int id);

	@Query("select ts.startDate from TutorialSession ts where ts.tutorial.id = :id")
	Collection<Date> findTutorialSessionsStartDateByTutorialId(int id);

	@Query("select ts.endDate from HandsOnSession ts where ts.tutorial.id = :id")
	Collection<Date> findHandsOnSessionsEndDateByTutorialId(int id);

	@Query("select ts.endDate from TutorialSession ts where ts.tutorial.id = :id")
	Collection<Date> findTutorialSessionsEndDateByTutorialId(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select t from Tutorial t where t.code = :code")
	Tutorial findTutorialByCode(String code);
}
