
package acme.testing.assistant.handsOnSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.HandsOnSession;
import acme.testing.TestHarness;

public class AssistantHandsOnSessionDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/handsOnSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final int handsOnSessionRecordIndex) {
		// HINT: this test authenticates as an assistant, lists his or her tutorials,
		// HINT: then selects one of them, go to his hands on sessions, select one and delete it.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(tutorialRecordIndex, 0, code);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Hands On Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(handsOnSessionRecordIndex);
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
		// HINT: this test tries to delete a hands on session of a tutorial that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<HandsOnSession> hands;
		String param;

		super.signIn("assistant1", "assistant1");
		hands = this.repository.findManyHandsOnSessionsByAssistantUsername("assistant2");
		for (final HandsOnSession hand : hands)
			if (hand.getTutorial().isDraftMode()) {
				param = String.format("id=%d", hand.getTutorial().getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/hands-on-session/delete", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/hands-on-session/delete", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/hands-on-session/delete", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
