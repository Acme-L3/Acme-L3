
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.course.Course;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedCourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.isPublished = TRUE")
	Collection<Course> findAllPublishedCourses();

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);
}
