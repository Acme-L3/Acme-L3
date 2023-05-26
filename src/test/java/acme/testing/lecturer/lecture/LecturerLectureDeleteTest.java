
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Override
	@BeforeAll
	public void beforeAll() {
		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnButton("Create Course");
		super.fillInputBoxIn("code", "ZZZ999");
		super.fillInputBoxIn("title", "titleTest");
		super.fillInputBoxIn("retailPrice", "EUR 12");
		super.fillInputBoxIn("abstractText", "abstractTest");
		super.fillInputBoxIn("courseType", "BALANCED");
		super.clickOnSubmit("Create");

		super.checkListingExists();

		super.signOut();
	}

	@Override
	@BeforeEach
	public void beforeEach() {
		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("List lectures in the course");
		super.checkListingExists();
		super.clickOnButton("Create Lecture");
		super.checkFormExists();
		super.fillInputBoxIn("title", "title");
		super.fillInputBoxIn("body", "body");
		super.fillInputBoxIn("abstractText", "abstractText");
		super.fillInputBoxIn("lectureType", "THEORY");
		super.fillInputBoxIn("estimateLearningTime", "2.00");
		super.clickOnSubmit("Create");

		super.signOut();
	}

	@Test
	public void test100Positive() {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("List lectures in the course");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("List lectures in the course");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@Test

	public void test200Negative() {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer2", "lecturer2");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("List lectures in the course");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotButtonExists("Delete");

		super.signOut();
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
