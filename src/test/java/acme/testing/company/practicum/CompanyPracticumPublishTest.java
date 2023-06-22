
package acme.testing.company.practicum;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumPublishTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "desc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Negative(final int recordIndex, final String code) {
		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotSubmitExists("Publish");

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final List<Practicum> practicums = this.repo.findNotPublishedPracticumsByCompany("company1");

		super.checkLinkExists("Sign in");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student1", "student1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company2", "company2");
		practicums.forEach(practicum -> {
			super.request("/company/practicum/publish", "id=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}

}
