
package acme.testing.student.enrolment;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentPublishTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String holderName, final String lowerNibble, final String expiryDate, final String CVC) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(1);
		super.checkFormExists();

		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("cvc", CVC);

		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String holderName, final String lowerNibble, final String expiryDate, final String CVC) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(2);
		super.checkFormExists();

		super.fillInputBoxIn("holderName", holderName);
		super.fillInputBoxIn("lowerNibble", lowerNibble);
		super.fillInputBoxIn("expiryDate", expiryDate);
		super.fillInputBoxIn("cvc", CVC);

		super.clickOnSubmit("Publish");
		super.checkErrorsExist();
		super.signOut();

	}

	@Test
	public void Test300Hacking() {

		final List<Enrolment> enrolments = this.repo.findEnrolmentByStudentName("student1");

		super.checkLinkExists("Sign in");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student2", "student2");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/publish", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}

	@Test
	public void test301Hacking() {

		Collection<Enrolment> enrolments;
		String params;

		super.signIn("student1", "student1");
		enrolments = this.repo.findEnrolmentByStudentName("student1");
		for (final Enrolment e : enrolments)
			if (!e.isDraftMode()) {
				params = String.format("id=%d", e.getId());
				super.request("/student/enrolment/publish", params);
			}
		super.signOut();
	}
}
