
package acme.features.authenticated.assistant.handsOnSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantHandsOnSessionDeleteService extends AbstractService<Assistant, HandsOnSession> {

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
		int handsOnSessionId;
		Tutorial tutorial;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		HandsOnSession object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findHandsOnSessionById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final HandsOnSession object) {
		assert object != null;
		super.bind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final HandsOnSession object) {
		assert object != null;
	}

	@Override
	public void perform(final HandsOnSession object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final HandsOnSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		tuple.put("id", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}

}
