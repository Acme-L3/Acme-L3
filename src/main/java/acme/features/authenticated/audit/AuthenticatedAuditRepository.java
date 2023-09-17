
package acme.features.authenticated.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.entitites.audits.Mark;
import acme.entitites.course.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select a from Audit a where a.course.id = :id")
	Collection<Audit> findAuditsByCourseId(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select au from Auditor au")
	Collection<Auditor> findAllAuditors();

	@Query("select ar.mark from AuditingRecord ar where ar.audit.id = :id")
	Collection<Mark> findMarksByAuditId(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);
}
