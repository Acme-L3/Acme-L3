
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String activityType, final String initDate, final String endDate, final String link) {
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Activities");
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String summary, final String activityType, final String initDate, final String endDate, final String link) {
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Activities");
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("activityType", activityType);
		super.fillInputBoxIn("initDate", initDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		final Collection<Enrolment> enrolments = this.repository.findEnrolmentWithoutDraftMode("student1");
		final Enrolment enrolment = enrolments.stream().findFirst().orElse(null);
		String param;
		param = String.format("id=%d", enrolment.getId());

		super.checkLinkExists("Sign in");
		super.request("/student/activity/create", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/create", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/create", param);
		super.checkPanicExists();
		super.signOut();
	}
	@Test
	public void test301Hacking() {

		final Collection<Enrolment> enrolments = this.repository.findEnrolmentWithDrafMode("student1");
		final Enrolment enrolment = enrolments.stream().findFirst().orElse(null);
		String param;
		param = String.format("id=%d", enrolment.getId());

		super.signIn("administrator", "administrator");
		super.request("/student/activity/create", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/create", param);
		super.checkPanicExists();
		super.signOut();
	}

}
