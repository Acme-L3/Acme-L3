
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Test
	public void test100Positive() {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Lectures");
		super.checkListingExists();

		super.clickOnButton("Create Lecture");
		super.checkFormExists();
		super.fillInputBoxIn("title", "Atitle");
		super.fillInputBoxIn("body", "body");
		super.fillInputBoxIn("abstractText", "abstractText");
		super.fillInputBoxIn("lectureType", "THEORY");
		super.fillInputBoxIn("estimateLearningTime", "2.00");
		super.clickOnSubmit("Create");
		super.checkListingExists();
		super.clickOnListingRecord(0);

		super.clickOnSubmit("Delete");
		super.checkNotPanicExists();

		super.signOut();
	}

	@Test

	public void test200Negative() {

		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Lectures");
		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkNotButtonExists("Delete");

		super.signOut();
	}

	@Test
	public void test300Hacking() {

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
