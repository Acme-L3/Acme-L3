
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		int masterId;
		Collection<Practicum> practicum;
		boolean status;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findAllPracticumsByCourseIdPublished(masterId);
		status = practicum != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int courseId;
		Collection<Practicum> practicums;

		courseId = super.getRequest().getData("masterId", int.class);
		practicums = this.repo.findAllPracticumsByCourseIdPublished(courseId);

		super.getBuffer().setData(practicums);
		super.getResponse().setGlobal("masterId", courseId);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;
		int courseId;

		courseId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(object, "code", "title", "summary");
		tuple.put("masterId", courseId);

		super.getResponse().setData(tuple);
	}

}
