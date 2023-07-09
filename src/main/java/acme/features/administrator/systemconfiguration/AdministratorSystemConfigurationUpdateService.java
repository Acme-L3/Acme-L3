
package acme.features.administrator.systemconfiguration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemconfiguration.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final SystemConfiguration object = this.repository.findSystemConfiguration().get(0);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;
		super.bind(object, "systemCurrency", "acceptedCurrencies");
	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;
		// SystemCurrency has pattern
		if (super.getBuffer().getErrors().hasErrors("systemCurrency"))
			super.state(!object.getSystemCurrency().matches("^[A-Z]{3}$"), "systemCurrency", "administrator.system-configuration.form.error.pattern.system-currency");
		final boolean b = Arrays.asList(object.getAcceptedCurrencies().split(",")).contains(object.getSystemCurrency());
		super.state(b, "systemCurrency", "administrator.system-configuration.form.error.not-found-in-list");
		// AcceptedCurrencies has pattern
		if (super.getBuffer().getErrors().hasErrors("acceptedCurrencies"))
			super.state(!object.getAcceptedCurrencies().matches("^[A-Z]{3}(,\\s*[A-Z]{3})*\\s*$"), "acceptedCurrencies", "administrator.system-configuration.form.error.pattern.accepted-currencies");
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		object.getAcceptedCurrencies().replaceAll("\\s", "");

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");
		super.getResponse().setData(tuple);
	}

}
