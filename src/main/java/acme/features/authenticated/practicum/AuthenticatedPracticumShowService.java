
package acme.features.authenticated.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumShowService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	protected AuthenticatedPracticumRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		int practicumId;
		Practicum practicum;
		boolean status;

		practicumId = super.getRequest().getData("id", int.class);
		practicum = this.repo.findPracticumById(practicumId);
		status = practicum != null && !practicum.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Practicum object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Practicum object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "summary", "goals");

		tuple.put("companyName", object.getCompany().getName());
		tuple.put("VATnumber", object.getCompany().getVATnumber());
		tuple.put("companySummary", object.getCompany().getSummary());
		tuple.put("link", object.getCourse().getLink());
		super.getResponse().setData(tuple);
	}

}
