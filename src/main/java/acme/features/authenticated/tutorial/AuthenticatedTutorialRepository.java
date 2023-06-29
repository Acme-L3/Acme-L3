
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.course.Course;
import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t where t.course.id = :courseId AND t.draftMode = FALSE")
	Collection<Tutorial> findAllTutorialsByCourseIdPublished(int courseId);

	@Query("select ts from TutorialSession ts where ts.tutorial.id = :id")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

}
