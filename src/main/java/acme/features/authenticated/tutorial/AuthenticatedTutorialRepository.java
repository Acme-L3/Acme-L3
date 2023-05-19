
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.course.Course;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findTutorialById(int id);

	@Query("select t from Tutorial t where t.course IS NOT NULL")
	Collection<Tutorial> findTutorialWithCourse();

	@Query("select t from Tutorial t where t.draftMode = false and t.course IS NOT NULL")
	Collection<Tutorial> findTutorialsAviability();

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select au from Assistant au")
	Collection<Assistant> findAllAssistants();

	@Query("select t.assistant from Tutorial t where t.id = :id")
	Assistant findAssistantByTutorial(int id);

}
