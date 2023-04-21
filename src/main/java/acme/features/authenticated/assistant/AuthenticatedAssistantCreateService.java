
package acme.features.authenticated.assistant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AuthenticatedAssistantCreateService extends AbstractService<Authenticated, Assistant> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAssistantRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = !super.getRequest().getPrincipal().hasRole(Assistant.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Assistant object;
		Principal principal;
		int uaId;
		final UserAccount ua;

		principal = super.getRequest().getPrincipal();
		uaId = principal.getAccountId();
		ua = this.repository.findUserAccountById(uaId);

		object = new Assistant();
		object.setUserAccount(ua);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Assistant object) {
		assert object != null;
		super.bind(object, "supervisor", "curriculum", "link");
	}

	@Override
	public void validate(final Assistant object) {
		assert object != null;
	}

	@Override
	public void perform(final Assistant object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Assistant object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "supervisor", "curriculum", "link");
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
