
package acme.testing.company.practicum;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumShowTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String course, final String practicumCode, final String practicumTitle, final String summary, final String goals) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("code", practicumCode);
		super.checkInputBoxHasValue("title", practicumTitle);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("goals", goals);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		final List<Practicum> practicums = this.repo.findPracticumsByCompany("company1");

		super.checkLinkExists("Sign in");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/show", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}

}
