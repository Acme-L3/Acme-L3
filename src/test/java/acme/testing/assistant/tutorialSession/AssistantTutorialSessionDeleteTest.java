
package acme.testing.assistant.tutorialSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.TutorialSession;
import acme.testing.TestHarness;

public class AssistantTutorialSessionDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorialSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final int tutorialSessionRecordIndex) {
		// HINT: this test authenticates as an assistant, lists his or her tutorials,
		// HINT: then selects one of them, go to his theory sessions, select one and delete it.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(tutorialSessionRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();

		super.signOut();
	}

	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to delete a theory session of a tutorial that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<TutorialSession> theorys;
		String param;

		super.checkLinkExists("Sign in");
		theorys = this.repository.findManyTutorialSessionsByAssistantUsername("assistant1");
		for (final TutorialSession theory : theorys)
			if (theory.getTutorial().isDraftMode()) {
				param = String.format("id=%d", theory.getTutorial().getId());

				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/tutorial-session/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
