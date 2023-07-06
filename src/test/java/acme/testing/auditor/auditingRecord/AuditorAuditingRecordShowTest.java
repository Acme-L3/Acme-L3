
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordShowTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final String code, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark,
		final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialMoment", initialMoment);
		super.checkInputBoxHasValue("finalMoment", finalMoment);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecord.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/auditor/auditing-record/show", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
