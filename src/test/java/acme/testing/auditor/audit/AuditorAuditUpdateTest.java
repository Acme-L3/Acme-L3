
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditUpdateTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int newRecordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String course) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(newRecordIndex, 0, code);
		super.checkColumnHasValue(newRecordIndex, 1, conclusion);
		super.checkColumnHasValue(newRecordIndex, 2, strongPoints);

		super.clickOnListingRecord(newRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("course", course);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String course) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("conclusion", conclusion);
		super.fillInputBoxIn("strongPoints", strongPoints);
		super.fillInputBoxIn("weakPoints", weakPoints);
		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();
		super.checkNotPanicExists();

		super.signOut();

	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a audit with a role other than "Auditor",or using an auditor who is not the owner.

		Collection<AuditingRecord> auditingRecords;
		String param;

		super.signIn("auditor1", "auditor1");
		auditingRecords = this.repository.findAuditingRecordsByAuditorUsername("auditor2");
		for (final AuditingRecord auditingRecord : auditingRecords)
			if (auditingRecord.getAudit().isDraftMode()) {

				param = String.format("id=%d", auditingRecord.getAudit().getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/auditor/auditing-records/update", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
