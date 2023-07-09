
package acme.testing.lecturer.course;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String retailPrice, final String abstractText, final String courseType, final String link) {
		// HINT: this test authenticates as a lecturer and then lists his or her
		// HINT: courses, creates a new one, and check that it's been created properly.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnButton("Create Course");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String title, final String retailPrice, final String abstractText, final String courseType, final String link) {
		// HINT: this test attempts to create tutorials with incorrect data.

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "My Courses");
		super.checkListingExists();

		super.clickOnButton("Create Course");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("abstractText", abstractText);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a tutorials using principals with
		// HINT+ inappropriate roles.

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("student1", "student1");
		super.request("/lecturer/course/create");
		super.checkPanicExists();
		super.signOut();
	}

}
