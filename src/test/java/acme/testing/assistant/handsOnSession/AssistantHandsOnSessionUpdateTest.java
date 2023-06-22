
package acme.testing.assistant.handsOnSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.session.HandsOnSession;
import acme.testing.TestHarness;

public class AssistantHandsOnSessionUpdateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/handsOnSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final String code, final int handsOnSessionRecordIndex, final String tittle, final String summary, final String creationMoment, final String startDate, final String endDate,
		final String link) {
		// HINT: this test signs in as an assistant, lists his or her tutorials, selects
		// HINT+ one of them and update it.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Hands On Sessions");
		super.checkListingExists();
		super.clickOnListingRecord(handsOnSessionRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.checkColumnHasValue(handsOnSessionRecordIndex, 0, tittle);
		super.checkColumnHasValue(handsOnSessionRecordIndex, 1, summary);

		super.clickOnListingRecord(handsOnSessionRecordIndex);
		super.checkInputBoxHasValue("tittle", tittle);
		super.checkInputBoxHasValue("summary", summary);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}

	public void test200Negative() {
		// HINT: The framework does not give enough support for this test.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to update a hands on session of a tutorial that is in draft mode or
		// HINT+ not available, but wasn't published by the principal;

		Collection<HandsOnSession> hands;
		String param;

		super.signIn("assistant1", "assistant1");
		hands = this.repository.findManyHandsOnSessionsByAssistantUsername("assistant2");
		for (final HandsOnSession hand : hands)
			if (hand.getTutorial().isDraftMode()) {
				param = String.format("id=%d", hand.getTutorial().getId());

				super.checkLinkExists("Sign in");
				super.request("/assistant/hands-on-session/update", param);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/assistant/hands-on-session/update", param);
				super.checkPanicExists();
				super.signOut();

				super.signIn("lecturer1", "lecturer1");
				super.request("/assistant/hands-on-session/update", param);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
