
package acme.features.administrator.banner;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Banner object = this.repository.findBannerById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		super.bind(object, "initMoment", "endMoment", "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setEndMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "initMoment", "endMoment", "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
		super.getResponse().setData(tuple);
	}

}
