
package acme.testing.student.enrolment;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentDeleteTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course) {

		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("courseShow", course);
		super.clickOnSubmit("Delete");

		super.checkListingExists();
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int enrolmentIndex, final String code, final String motivation, final String goals) {
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(enrolmentIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);

		super.checkNotButtonExists("Delete");

		super.signOut();
	}
	@Test
	public void test300Hacking() {

		final List<Enrolment> enrolments = this.repo.findEnrolmentByStudentName("student1");

		super.checkLinkExists("Sign in");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student2", "student2");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		enrolments.forEach(e -> {
			super.request("/student/enrolment/delete", "id=" + e.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

}
