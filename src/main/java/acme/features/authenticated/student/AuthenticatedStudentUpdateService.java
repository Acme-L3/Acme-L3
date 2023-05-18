
package acme.features.authenticated.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedStudentUpdateService extends AbstractService<Authenticated, Student> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedStudentRepository repo;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Student object;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		object = this.repo.findStudentByUserAccountId(userAccountId);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Student object) {
		assert object != null;

		super.bind(object, "statement", "strongFeatures", "weakFeatures", "link");
	}

	@Override
	public void validate(final Student object) {
		assert object != null;
	}

	@Override
	public void perform(final Student object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Student object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "statement", "strongFeatures", "weakFeatures", "link");

		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
