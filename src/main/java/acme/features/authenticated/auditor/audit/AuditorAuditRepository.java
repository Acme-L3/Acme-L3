
package acme.features.authenticated.auditor.audit;

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
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("select au from Auditor au where au.id = :id")
	Auditor findAuditorById(int id);

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select a.course.title from Audit a where a.id = :id")
	String findCourseTitleByAuditId(int id);

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :id")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int id);

	@Query("select a from Audit a where a.auditor.id = :id")
	Collection<Audit> findAuditsByAuditorId(int id);

	@Query("select a from Audit a where a.code = :code")
	Audit findAuditByCode(String code);

	@Query("select ar.mark from AuditingRecord ar where ar.audit.id = :id")
	Collection<Mark> findMarksByAuditId(int id);

}
