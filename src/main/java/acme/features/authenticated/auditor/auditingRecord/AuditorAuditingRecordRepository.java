
package acme.features.authenticated.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("select a from Audit a where a.id = :id")
	Audit findAuditById(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.id = :auditId")
	Collection<AuditingRecord> findAuditingRecordsByAuditId(int auditId);

	@Query("select ar.audit from AuditingRecord ar where ar.id = :auditingRecordId")
	Audit findAuditByAuditingRecordId(int auditingRecordId);

	@Query("select ar from AuditingRecord ar where ar.id = :auditingRecordId")
	AuditingRecord findAuditingRecordById(int auditingRecordId);

}
