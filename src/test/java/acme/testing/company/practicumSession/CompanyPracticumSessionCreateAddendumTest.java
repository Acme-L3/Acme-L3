
package acme.testing.company.practicumSession;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanyPracticumSessionCreateAddendumTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-addendum-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String initialDate, final String endDate, final String link, final String check) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.checkButtonExists("Create Addendum");
		super.clickOnButton("Create Addendum");

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("check", check);
		super.clickOnSubmit("Create Addendum");

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
	@CsvFileSource(resources = "/company/practicum-session/create-addendum-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String initialDate, final String endDate, final String link, final String check) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(1);

		super.clickOnButton("Practicum Sessions");

		super.checkListingExists();
		super.checkButtonExists("Create Addendum");
		super.clickOnButton("Create Addendum");

		super.checkFormExists();
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("initialDate", initialDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("check", check);

		super.clickOnSubmit("Create Addendum");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum-session/create-addendum-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String title) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Company", "My practicums");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Practicum Sessions");
		super.checkListingExists();
		super.checkNotButtonExists("Create Addendum");

		super.signOut();
	}

}
