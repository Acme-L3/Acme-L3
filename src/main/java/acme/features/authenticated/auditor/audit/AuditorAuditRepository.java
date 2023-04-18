
package acme.features.authenticated.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select au from Auditor au where au.id = :id")
	Auditor findAuditorById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

}
