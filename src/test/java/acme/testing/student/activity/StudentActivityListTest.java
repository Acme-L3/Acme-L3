
package acme.testing.student.activity;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;
import acme.testing.student.enrolment.StudentEnrolmentTestRepository;

public class StudentActivityListTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int activityIndex, final String title, final String summary, final String activityType, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "My enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.clickOnButton("Activities");

		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(activityIndex, 0, title);
		super.checkColumnHasValue(activityIndex, 1, summary);
		super.checkColumnHasValue(activityIndex, 2, activityType);
		super.checkColumnHasValue(activityIndex, 3, link);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		final List<Enrolment> enrolments = this.repo.findEnrolmentByStudentName("student1");

		super.checkLinkExists("Sign in");
		enrolments.forEach(activity -> {
			super.request("/student/activity/list", "enrolmentId=" + activity.getId());
			super.checkPanicExists();
		});

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		enrolments.forEach(activity -> {
			super.request("/student/activity/list", "enrolmentId=" + activity.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("company1", "company1");
		enrolments.forEach(activity -> {
			super.request("/student/activity/list", "enrolmentId=" + activity.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("lecturer1", "lecturer1");
		enrolments.forEach(activity -> {
			super.request("/student/activity/list", "enrolmentId=" + activity.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		enrolments.forEach(activity -> {
			super.request("/student/activity/list", "enrolmentId=" + activity.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		enrolments.forEach(practicum -> {
			super.request("/student/activity/list", "enrolmentId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("student2", "student2");
		enrolments.forEach(practicum -> {
			super.request("/student/activity/list", "enrolmentId=" + practicum.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
