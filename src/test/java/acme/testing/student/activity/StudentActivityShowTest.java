
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentActivityShowTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repo;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String summary, final String activityType, final String initDate, final String endDate, final String moreInfo) {
		super.signIn("student1", "student1");
		super.clickOnMenu("Student", "My enrolments");

		super.checkListingExists();

		super.clickOnListingRecord(1);
		super.checkFormExists();
		super.clickOnButton("Activities");

		super.clickOnListingRecord(1);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("activityType", activityType);
		super.checkInputBoxHasValue("initDate", initDate);
		super.checkInputBoxHasValue("endDate", endDate);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//There are not any negative test cases for this feature
	}

	@Test
	public void test300Hacking() {
		Activity activity = null;
		final Collection<Enrolment> enrolments = this.repo.findEnrolemntByStudentUsername("student1");
		for (final Enrolment e : enrolments) {
			final Collection<Activity> activities = this.repo.findActivityByEnrolment(e.getId());
			if (activities != null) {
				activity = activities.stream().findFirst().orElse(null);
				break;
			}
		}
		String param;
		param = String.format("id=%d", activity.getId());

		super.checkLinkExists("Sign in");
		super.request("/student/activity/show", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("student2", "student2");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("assistant1", "assistant1");
		super.request("/student/activity/show", param);
		super.checkPanicExists();
		super.signOut();
	}

}
