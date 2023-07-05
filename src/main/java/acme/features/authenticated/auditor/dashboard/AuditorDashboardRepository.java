
package acme.features.authenticated.auditor.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.userAccount.id = :accountId")
	Auditor findAuditorByAccountId(int accountId);

	@Query("select count(a) from Audit a where a.auditor.id = :id AND a.course.courseType = 0")
	int totalNumberOfTheoryAudits(int id);

	@Query("select count(a) from Audit a where a.auditor.id = :id AND a.course.courseType = 1")
	int totalNumberOfHandsOnAudits(int id);

	@Query("select count(a) from Audit a where a.auditor.id = :id AND a.course.courseType = 2")
	int totalNumberOfBalancedAudits(int id);

	@Query("select avg(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double averageNumberOfAuditingRecords(int id);

	@Query("select count(ar) from AuditingRecord ar where ar.audit.auditor.id = :id group by ar.audit ")
	List<Integer> numberOfRecordsByAudit(int id);

	@Query("select min(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double minimumNumberOfAuditingRecords(int id);

	@Query("select max(select count(ar) from AuditingRecord ar where ar.audit.id = a.id) from Audit a where a.auditor.id = :id")
	Double maximumNumberOfAuditingRecords(int id);

	@Query("select ar from AuditingRecord ar where ar.audit.auditor.id = :id")
	List<AuditingRecord> findAllAuditingRecordsByAuditorId(int id);

	default Double deviationOfAuditingRecords(final int id) {
		final List<Integer> numberOfRecords = this.numberOfRecordsByAudit(id);
		final Double average = numberOfRecords.stream().mapToInt(Integer::intValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = numberOfRecords.stream().map(n -> Math.pow(n - average, 2.)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double averageTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromPeriod).average().orElse(0);
	}

	default Double timeDeviationOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		final List<Double> hours = records.stream().map(AuditingRecord::getHoursFromPeriod).collect(Collectors.toList());
		final Double average = hours.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = hours.stream().map(h -> Math.pow(h - average, 2)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double minimumTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromPeriod).min().orElse(0);
	}

	default Double maximumTimeOfAuditingRecords(final int id) {
		final List<AuditingRecord> records = this.findAllAuditingRecordsByAuditorId(id);
		return records.stream().mapToDouble(AuditingRecord::getHoursFromPeriod).max().orElse(0);
	}

}
