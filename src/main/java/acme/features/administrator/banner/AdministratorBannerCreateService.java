
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.banner.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Errors;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerCreateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Banner object;
		object = new Banner();
		final Date moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		super.bind(object, "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
		final Errors errors = super.getBuffer().getErrors();

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			final boolean b = object.getStartDate().after(MomentHelper.getCurrentMoment());
			super.state(b, "startDate", "administrator.banner.form.error.startDate-past");
		}
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 7, ChronoUnit.DAYS) && object.getStartDate().before(object.getEndDate()), "endDate", "administrator.banner.form.error.endDate-not-long-enough");

	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		final Date moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		final Tuple tuple = super.unbind(object, "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
		super.getResponse().setData(tuple);
	}

}
