
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;

import acme.testing.TestHarness;

public class LecturerCourseLectureTest extends TestHarness {

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
		super.clickOnListingRecord(4);
		super.checkFormExists();
		super.clickOnButton("Add lecture");
		super.checkFormExists();
		super.fillInputBoxIn("lecture", "Data Visualization");
		super.clickOnSubmit("Add lecture");
		super.checkFormExists();
		super.clickOnButton("Remove lecture");
		super.checkFormExists();
		super.fillInputBoxIn("lecture", "Data Visualization");
		super.clickOnSubmit("Remove lecture");

		super.signOut();
	}

	@Test
	public void test200OnlyTheorical() {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.checkNotButtonExists("Add lecture");
		super.checkNotButtonExists("Remove lecture");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorials using principals with
		// HINT+ inappropriate roles.

	}

}
