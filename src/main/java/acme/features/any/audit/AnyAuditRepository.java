
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.audits.Audit;
import acme.entitites.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AnyAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select a from Audit a where a.course IS NOT NULL")
	Collection<Audit> findAuditsWithCourse();

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select au from Auditor au")
	Collection<Auditor> findAllAuditors();
}
