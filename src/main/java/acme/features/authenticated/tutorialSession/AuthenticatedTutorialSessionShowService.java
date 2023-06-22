
package acme.features.authenticated.tutorialSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialSessionShowService extends AbstractService<Authenticated, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final int tutorialSessionId;
		Tutorial tutorial;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByTutorialSessionId(tutorialSessionId);
		status = tutorial != null && !tutorial.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession objects;
		int tutorialSessionId;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		objects = this.repository.findTutorialSessionById(tutorialSessionId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		super.getResponse().setData(tuple);
	}

}
