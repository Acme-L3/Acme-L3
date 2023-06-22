
package acme.testing.company.practicumSession;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.practicums.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanyPracticumSessionListTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String addendum) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, summary);
		super.checkColumnHasValue(recordIndex, 2, addendum);

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
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		practicums.forEach(practicum -> {
			super.request("/company/practicum-session/list", "masterId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
