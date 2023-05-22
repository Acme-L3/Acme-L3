
package acme.features.authenticated.assistant.handsOnSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantHandsOnSessionShowService extends AbstractService<Assistant, HandsOnSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final int handsOnSessionId;
		final Tutorial tutorial;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		HandsOnSession object;
		int handsOnSessionId;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findHandsOnSessionById(handsOnSessionId);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final HandsOnSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		tuple.put("tutorialId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
