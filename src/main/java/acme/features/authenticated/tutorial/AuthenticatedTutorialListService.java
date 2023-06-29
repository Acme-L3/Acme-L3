
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		int masterId;
		Collection<Tutorial> tutorial;
		boolean status;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findAllTutorialsByCourseIdPublished(masterId);
		status = tutorial != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Tutorial> objects;
		int courseId;
		courseId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findAllTutorialsByCourseIdPublished(courseId);
		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("masterId", courseId);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		final Tuple tuple = super.unbind(object, "code", "tittle", "summary");
		super.getResponse().setData(tuple);
	}
}
