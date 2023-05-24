
package acme.testing.assistant.handsOnSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entitites.tutorial.Tutorial;
import acme.testing.TestHarness;

public class AssistantHandsOnSessionCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/handsOnSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int tutorialRecordIndex, final int handsOnSessionRecordIndex, final String tittle, final String summary, final String creationMoment, final String startDate, final String endDate, final String link) {
		// HINT: this test authenticates as an assistant, list his or her tutorials, navigates
		// HINT+ to their hands on sessions, and checks that they have the expected data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();

		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Hands On Sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");

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

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/handsOnSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int tutorialRecordIndex, final int handsOnSessionRecordIndex, final String tittle, final String summary, final String creationMoment, final String startDate, final String endDate, final String link) {
		// HINT: this test attempts to create hands on sessions using wrong data.

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My Tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(tutorialRecordIndex);
		super.clickOnButton("Hands On Sessions");

		super.clickOnButton("Create");
		super.fillInputBoxIn("tittle", tittle);
		super.fillInputBoxIn("summary", summary);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to create a hands on session for a tutorial as a principal without 
		// HINT: the "Assistant" role.

		Collection<Tutorial> tutorials;
		String param;

		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());

			super.checkLinkExists("Sign in");
			super.request("/assistant/hands-on-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/assistant/hands-on-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer1", "lecturer1");
			super.request("/assistant/hands-on-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

	@Test
	public void test301Hacking() {
		// HINT: this test tries to create a hands on session for a published tutorial created by 
		// HINT+ the principal.

		Collection<Tutorial> tutorials;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant2", "assistant2");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant2");
		for (final Tutorial tutorial : tutorials)
			if (!tutorial.isDraftMode()) {
				param = String.format("masterId=%d", tutorial.getId());
				super.request("/assistant/hands-on-session/create", param);
				super.checkPanicExists();
			}
	}

	@Test
	public void test302Hacking() {
		// HINT: this test tries to create hands on sessions for tutorials that weren't created 
		// HINT+ by the principal.

		Collection<Tutorial> tutorials;
		String param;

		super.checkLinkExists("Sign in");
		super.signIn("assistant1", "assistant1");
		tutorials = this.repository.findManyTutorialsByAssistantUsername("assistant2");
		for (final Tutorial tutorial : tutorials) {
			param = String.format("masterId=%d", tutorial.getId());
			super.request("/assistant/hands-on-session/create", param);
			super.checkPanicExists();
		}
	}

}
