
package acme.testing.auditor.audit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints) {
		// HINT: this test signs in as an auditor, lists all of the audits, 
		// HINT+ and then checks that the listing shows the expected data.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My Audits");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, conclusion);
		super.checkColumnHasValue(recordIndex, 2, strongPoints);
		super.checkColumnHasValue(recordIndex, 3, weakPoints);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list all of the audits using 
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/auditor/audit/list");
		super.checkPanicExists();
		super.signOut();
	}

}
