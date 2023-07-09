
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndexLecture, final String title, final String abstractText, final String estimateLearningTime, final String body, final String lectureType, final String link, final String user) {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn(user, user);

		super.clickOnMenu("Lecturer", "My Lectures");
		super.checkListingExists();

		super.checkListingExists();
		super.checkColumnHasValue(recordIndexLecture, 0, title);
		super.checkColumnHasValue(recordIndexLecture, 2, body);
		super.checkColumnHasValue(recordIndexLecture, 1, abstractText);
		super.checkColumnHasValue(recordIndexLecture, 3, lectureType);
		super.checkColumnHasValue(recordIndexLecture, 4, estimateLearningTime);
		super.checkColumnHasValue(recordIndexLecture, 5, link);

		super.signOut();
	}

	@Test

	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it's a listing
		// HINT+ that doesn't involve entering any data in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorials using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/lecture/list");
		super.checkPanicExists();
		super.signOut();
	}

}
