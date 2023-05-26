
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCorrection extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/correction-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnButton("Create correction");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialMoment", initialMoment);
		super.fillInputBoxIn("finalMoment", finalMoment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.checkColumnHasValue(auditingRecordsRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordsRecordIndex, 1, mark);
		super.checkColumnHasValue(auditingRecordsRecordIndex, 2, "true");

		super.clickOnListingRecord(auditingRecordsRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialMoment", initialMoment);
		super.checkInputBoxHasValue("finalMoment", finalMoment);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/correction-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.clickOnButton("Create correction");

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialMoment", initialMoment);
		super.fillInputBoxIn("finalMoment", finalMoment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits) {
			param = String.format("auditId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {

		Collection<Audit> audits;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		audits = this.repository.findAuditsByAuditorUsername("auditor2");
		for (final Audit audit : audits) {
			param = String.format("auditId=%d", audit.getId());
			super.request("/auditor/auditing-record/create-correction", param);
			super.checkPanicExists();
		}
	}

}
