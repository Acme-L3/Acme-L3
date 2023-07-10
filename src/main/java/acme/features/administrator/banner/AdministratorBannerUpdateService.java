
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
		super.bind(object, "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {

			final Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, 2000);
			calendar1.set(Calendar.MONTH, Calendar.JANUARY);
			calendar1.set(Calendar.DAY_OF_MONTH, 1);
			calendar1.set(Calendar.HOUR_OF_DAY, 00);
			calendar1.set(Calendar.MINUTE, 00);
			calendar1.set(Calendar.SECOND, 00);
			calendar1.set(Calendar.MILLISECOND, 0);
			final Date iniDate = calendar1.getTime();
			final Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2100);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 0);
			final Date limitDate = calendar.getTime();
			boolean date1;
			boolean date2;
			date1 = MomentHelper.isAfterOrEqual(object.getStartDate(), iniDate);
			date2 = MomentHelper.isBeforeOrEqual(object.getEndDate(), limitDate);
			super.state(date1 && date2, "startDate", "administrator.baner.form.error.limit.date");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(object.getStartDate().after(MomentHelper.getCurrentMoment()), "startDate", "adminitrator.banner.form.error.startDate-past");
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 7, ChronoUnit.DAYS), "endDate", "adminitrator.banner.form.error.endDate-not-long-enough");
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setMoment(moment);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "moment", "startDate", "endDate", "linkPhoto", "slogan", "linkDocument");
		super.getResponse().setData(tuple);
	}

}
