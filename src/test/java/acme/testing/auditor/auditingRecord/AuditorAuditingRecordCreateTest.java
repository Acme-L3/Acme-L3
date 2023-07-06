
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");

		super.clickOnButton("Create");
		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("initialMoment", initialMoment);
		super.fillInputBoxIn("finalMoment", finalMoment);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);
		super.checkColumnHasValue(auditingRecordRecordIndex, 2, mark);

		super.clickOnListingRecord(auditingRecordRecordIndex);
		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialMoment", initialMoment);
		super.checkInputBoxHasValue("finalMoment", finalMoment);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-positive-correction.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100PositiveCorrection(final int auditRecordIndex, final int auditingRecordRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link,
		final String confirmation) {

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
		super.fillInputBoxIn("confirmation", confirmation);

		super.clickOnSubmit("Create Auditing Record correction");

		super.checkListingExists();

		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, assessment);
		super.checkColumnHasValue(auditingRecordRecordIndex, 2, mark);

		super.clickOnListingRecord(auditingRecordRecordIndex);

		super.checkInputBoxHasValue("subject", subject);
		super.checkInputBoxHasValue("assessment", assessment);
		super.checkInputBoxHasValue("initialMoment", initialMoment);
		super.checkInputBoxHasValue("finalMoment", finalMoment);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(auditRecordIndex);
		super.clickOnButton("Auditing Records");
		super.clickOnButton("Create");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-negative-correction.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int auditRecordIndex, final int auditingRecordsRecordIndex, final String subject, final String assessment, final String initialMoment, final String finalMoment, final String mark, final String link,
		final String confirmation) {

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
		super.fillInputBoxIn("confirmation", confirmation);

		super.clickOnSubmit("Create Auditing Record correction");
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
			param = String.format("masterId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
