
package acme.features.authenticated.bulletin;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.bulletins.Bulletin;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedBulletinShowService extends AbstractService<Authenticated, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedBulletinRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Bulletin bulletin;
		Date deadline;

		id = super.getRequest().getData("id", int.class);
		bulletin = this.repository.findOneBulletinById(id);
		deadline = MomentHelper.deltaFromCurrentMoment(-365, ChronoUnit.DAYS);
		status = MomentHelper.isAfter(bulletin.getMoment(), deadline);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Bulletin object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBulletinById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "moment", "title", "message", "critical", "link");

		super.getResponse().setData(tuple);
	}

}
