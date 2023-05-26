
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordListTest extends TestHarness {

	@Autowired
	protected AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecordIndex, final int auditingRecordRecordIndex, final String code, final String subject, final String mark, final String correction) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.checkColumnHasValue(auditRecordIndex, 0, code);
		super.clickOnListingRecord(auditRecordIndex);
		super.checkInputBoxHasValue("code", code);
		super.clickOnButton("Auditing Records");

		super.checkListingExists();
		super.checkColumnHasValue(auditingRecordRecordIndex, 0, subject);
		super.checkColumnHasValue(auditingRecordRecordIndex, 1, mark);
		super.checkColumnHasValue(auditingRecordRecordIndex, 2, correction);
		super.clickOnListingRecord(auditingRecordRecordIndex);

		super.signOut();

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (audit.isDraftMode()) {
				param = String.format("masterId=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/auditor/auditing-record/list", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
