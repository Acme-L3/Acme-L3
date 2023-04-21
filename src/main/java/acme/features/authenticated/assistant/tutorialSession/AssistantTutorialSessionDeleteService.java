
package acme.features.authenticated.assistant.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionDeleteService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int tutorialSessionId;
		Tutorial tutorial;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByTutorialSessionId(tutorialSessionId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialSessionById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;
	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		tuple.put("id", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
