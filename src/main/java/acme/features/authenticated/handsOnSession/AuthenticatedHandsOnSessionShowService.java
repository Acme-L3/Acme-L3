
package acme.features.authenticated.handsOnSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedHandsOnSessionShowService extends AbstractService<Authenticated, HandsOnSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedHandsOnSessionRepository repository;

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
		final int handsOnSessionId;
		Tutorial tutorial;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
		status = tutorial != null && !tutorial.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		HandsOnSession objects;
		int handsOnSessionId;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		objects = this.repository.findHandsOnSessionById(handsOnSessionId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final HandsOnSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		super.getResponse().setData(tuple);
	}

}
