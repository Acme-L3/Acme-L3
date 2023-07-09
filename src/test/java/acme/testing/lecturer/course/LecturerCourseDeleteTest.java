
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class LecturerCourseDeleteTest extends TestHarness {

	@Test
	public void test100Positive() {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnButton("Create Course");
		super.fillInputBoxIn("code", "ZSD999");
		super.fillInputBoxIn("title", "titleTest");
		super.fillInputBoxIn("retailPrice", "EUR 12");
		super.fillInputBoxIn("abstractText", "abstractTest");
		super.clickOnSubmit("Create");

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(4);
		super.checkFormExists();
		super.clickOnSubmit("Delete");

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: this test attempts to create tutorials with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.sortListing(0, "asc");
		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkNotButtonExists("Delete");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorials using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/delete");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course/delete");
		super.checkPanicExists();
		super.signOut();
	}

}
