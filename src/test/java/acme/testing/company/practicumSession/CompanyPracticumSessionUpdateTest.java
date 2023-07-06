
package acme.testing.company.practicumSession;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionUpdateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String initialDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.clickOnListingRecord(1);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkNotErrorsExist();
		super.sortListing(0, "desc");

		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("initialDate", initialDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String initialDate, final String endDate, final String link) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.clickOnListingRecord(1);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String title) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(1);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Update");

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final List<PracticumSession> practicumSessions = this.repo.findPracticumSessionsByCompany("company1");

		super.checkLinkExists("Sign in");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		practicumSessions.forEach(session -> {
			super.request("/company/practicum-session/update", "id=" + session.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
