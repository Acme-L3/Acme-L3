
package acme.features.authenticated.assistant.tutorialSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionListService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("tutorialId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final int tutorialId;
		final Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		status = tutorial != null && (!tutorial.isDraftMode() || super.getRequest().getPrincipal().hasRole(tutorial.getAssistant()));
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TutorialSession> objects;
		int tutorialId;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		objects = this.repository.findTutorialSessionsByTutorialId(tutorialId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<TutorialSession> objects) {
		assert objects != null;
		int tutorialId;
		Tutorial tutorial;
		boolean showCreate;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		showCreate = tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setGlobal("tutorialId", tutorialId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
