
package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.bulletins.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Bulletin object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Bulletin();
		object.setMoment(moment);
		object.setTitle("");
		object.setMessage("");
		object.setLink("");

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;
		super.bind(object, "moment", "title", "message", "critical", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;
		boolean confirmation;
		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "moment", "title", "message", "critical", "link");
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}

}
