
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordIndex) {
		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordIndex);
		super.checkFormExists();

		final String auditingRecordIdString = super.getCurrentQuery();
		final int auditingRecordId = Integer.parseInt(auditingRecordIdString.substring(auditingRecordIdString.indexOf("=") + 1));
		final String param = String.format("id=%d", auditingRecordId);

		super.checkNotSubmitExists("Delete");

		super.request("/auditor/auditing-record/delete", param);
		super.checkPanicExists();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecord> manyAuditingRecords;
		String param;

		manyAuditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor1");
		for (final AuditingRecord auditingRecord : manyAuditingRecords) {
			param = String.format("id=%d", auditingRecord.getAudit().getId());

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<AuditingRecord> manyAuditingRecords;
		String param;

		manyAuditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord auditingRecords : manyAuditingRecords)
			if (auditingRecords.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecords.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("assistant1", "assistant1");
				super.request("/auditor/auditing-record/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
