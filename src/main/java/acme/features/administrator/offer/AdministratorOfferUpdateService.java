
package acme.features.administrator.offer;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemconfiguration.SystemConfiguration;
import acme.entitites.offers.Offer;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository					repository;

	@Autowired
	protected AdministratorSystemConfigurationRepository	systemConfigurationRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Offer object = this.repository.findOfferById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Offer object) {
		assert object != null;
		super.bind(object, "moment", "heading", "summary", "startAvailability", "endAvailability", "price", "link");
	}

	@Override
	public void validate(final Offer object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("moment")) {
			Date moment;

			moment = MomentHelper.getCurrentMoment();
			super.state(!MomentHelper.isAfter(object.getMoment(), moment), "moment", "administrator.offer.form.error.moment-must-be-past");
		}
		if (!super.getBuffer().getErrors().hasErrors("heading"))
			super.state(object.getHeading().length() <= 75, "heading", "administrator.offer.form.error.heading-lenght");
		if (!super.getBuffer().getErrors().hasErrors("summary"))
			super.state(object.getSummary().length() <= 100, "summary", "administrator.offer.form.error.summary-lenght");
		if (!super.getBuffer().getErrors().hasErrors("startAvailability")) {
			Date startDate;

			startDate = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfter(object.getStartAvailability(), startDate), "startAvailability", "administrator.offer.form.error.start-too-early");
		}
		if (!super.getBuffer().getErrors().hasErrors("endAvailability")) {
			Date endDate;
			final Calendar c = Calendar.getInstance();
			c.setTime(object.getStartAvailability());

			c.add(Calendar.DATE, 7);
			endDate = c.getTime();
			super.state(MomentHelper.isAfter(object.getEndAvailability(), endDate), "endAvailability", "administrator.offer.form.error.end-too-early");
		}
		if (!super.getBuffer().getErrors().hasErrors("price")) {
			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.negative-price");

			final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.findSystemConfiguration().get(0);
			final boolean b = Arrays.asList(systemConfiguration.getAcceptedCurrencies().split(",")).contains(object.getPrice().getCurrency());
			super.state(b, "price", "administrator.offer.form.error.not-found-currency");
		}
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "moment", "summary", "heading", "startAvailability", "endAvailability", "price", "link");
		super.getResponse().setData(tuple);
	}

}
