
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final int tutorialSessionRecordIndex, final String tittle, final String summary, final String sessionType, final String startDate, final String endDate, final String link) {
		// HINT: this test signs in as an assistant, lists his or her tutorials, selects
		// HINT+ one of them and update it.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(tutorialSessionRecordIndex, 0, tittle);
		super.checkColumnHasValue(tutorialSessionRecordIndex, 1, summary);

		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkInputBoxHasValue("tittle", tittle);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("sessionType", sessionType);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final String code, final int tutorialSessionRecordIndex, final String tittle, final String summary, final String sessionType, final String startDate, final String endDate, final String link) {
		// HINT: The framework does not give enough support for this test.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("sessionType", sessionType);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a theory session of a tutorial that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<TutorialSession> theorys;
		String param;

		super.checkLinkExists("Sign in");
		theorys = this.repository.findManyTutorialSessionsByAssistantUsername("assistant1");
		for (final TutorialSession theory : theorys)
			if (theory.getTutorial().isDraftMode()) {
				param = String.format("id=%d", theory.getTutorial().getId());

				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/tutorial-session/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
