
package acme.testing.company.practicumSession;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String initialDate, final String endDate, final String link) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		final List<PracticumSession> practicumSessions = this.repo.findPracticumSessionsByCompany("company1");

		super.checkLinkExists("Sign in");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/list", "id=" + session.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/list", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/show", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/show", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/show", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/show", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/show", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}

}
