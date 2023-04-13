
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Request;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


	@Override
	public void check() {
		boolean status;

		final Request req = super.getRequest();
		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		PracticumSession object;
		Principal principal;
		int practicumSessionId;

		practicumSessionId = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumSessionById(practicumSessionId);
		principal = super.getRequest().getPrincipal();

		status = object.getPracticum().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

}
