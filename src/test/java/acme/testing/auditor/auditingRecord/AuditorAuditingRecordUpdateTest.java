
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordsRecordIndex, final int newAuditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment,
		final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialMoment", initialMoment);
		super.fillInputBoxIn("finalMoment", finalMoment);
		super.fillInputBoxIn("mark", mark);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(newAuditingRecordsRecordIndex, 0, subject);
		super.checkColumnHasValue(newAuditingRecordsRecordIndex, 1, assessment);
		super.checkColumnHasValue(newAuditingRecordsRecordIndex, 2, mark);

		super.clickOnListingRecord(newAuditingRecordsRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialMoment", initialMoment);
		super.checkInputBoxHasValue("finalMoment", finalMoment);
		super.checkInputBoxHasValue("mark", mark);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.checkListingExists();
		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialMoment", initialMoment);
		super.fillInputBoxIn("finalMoment", finalMoment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();
	}

	//	@ParameterizedTest
	//	@CsvFileSource(resources = "/auditor/auditingRecord/update-negative-correction.csv", encoding = "utf-8", numLinesToSkip = 1)
	//	public void test201Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {
	//
	//		super.signIn("auditor1", "auditor1");
	//
	//		super.clickOnMenu("Auditor", "My Audits");
	//		super.checkListingExists();
	//		super.clickOnListingRecord(auditRecordIndex);
	//		super.clickOnButton("Auditing Records");
	//		super.checkListingExists();
	//		super.clickOnListingRecord(auditingRecordsRecordIndex);
	//		super.checkFormExists();
	//		super.fillInputBoxIn("subject", subject);
	//		super.fillInputBoxIn("assessment", assessment);
	//		super.fillInputBoxIn("initialMoment", initialMoment);
	//		super.fillInputBoxIn("finalMoment", finalMoment);
	//		super.fillInputBoxIn("mark", mark);
	//		super.fillInputBoxIn("link", link);
	//
	//		final String auditingRecordIdString = super.getCurrentQuery();
	//		final int auditingRecordId = Integer.parseInt(auditingRecordIdString.substring(auditingRecordIdString.indexOf("=") + 1));
	//		final String param = String.format("id=%d", auditingRecordId);
	//
	//		super.checkNotSubmitExists("Update");
	//
	//		super.request("/auditor/auditing-record/update", param);
	//		super.checkPanicExists();
	//
	//		super.signOut();
	//	}

	@Test
	public void test300Hacking() {

		Collection<AuditingRecord> auditingRecords;
		String param;

		auditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().isDraftMode()) {
				param = String.format("id=%d", auditingRecord.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/auditor/auditing-record/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
