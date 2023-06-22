
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditDeleteTest extends TestHarness {

	@Autowired
	protected AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();
		super.clickOnListingRecord(recordIndex);
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkListingExists();

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete an audit using principals with inappropriate roles

		final Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits) {
			param = String.format("id=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student1", "student1");
			super.request("/auditor/audit/delete", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to delete an audit with draftMode false

		Collection<Audit> audits;
		String param;

		audits = this.repository.findAuditsByAuditorUsername("auditor1");
		for (final Audit audit : audits)
			if (!audit.isDraftMode()) {
				param = String.format("id=%d", audit.getId());

				super.checkLinkExists("Sign in");
				super.request("/auditor/audit/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/auditor/audit/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor1", "auditor1");
				super.request("/auditor/audit/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("auditor2", "auditor2");
				super.request("/auditor/audit/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("student1", "student1");
				super.request("/auditor/audit/delete", param);
				super.checkPanicExists();
				super.signOut();

			}
	}

}
