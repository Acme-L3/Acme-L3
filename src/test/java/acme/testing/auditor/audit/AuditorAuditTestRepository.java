
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("select a from Audit a where a.auditor.userAccount.username = :username")
	Collection<Audit> findAuditsByAuditorUsername(String username);

	@Query("select ar from AuditingRecord ar where ar.audit.auditor.userAccount.username = :username")
	Collection<AuditingRecord> findAuditingRecordsByAuditorUsername(String username);

	@Query("select a from Audit a where a.code=:code")
	Audit findAuditByCode(String code);

}
