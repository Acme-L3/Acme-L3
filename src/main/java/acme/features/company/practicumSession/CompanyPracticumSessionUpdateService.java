
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.entitites.session.PracticumSession;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		PracticumSession object;
		Practicum practicum;
		Principal principal;
		int practicumSessionId;

		practicumSessionId = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumSessionById(practicumSessionId);
		practicum = object.getPracticum();
		principal = super.getRequest().getPrincipal();

		status = practicum.getCompany().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		PracticumSession object;
		int id;
		Practicum practicum;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findPracticumSessionById(id);
		practicum = this.repo.findPracticumById(object.getPracticum().getId());

		super.getBuffer().setData(object);
		super.getResponse().setGlobal("draftMode", practicum.getDraftMode());
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		int practicumSessionId;
		Practicum practicum;

		practicumSessionId = super.getRequest().getData("id", int.class);
		practicum = this.repo.findPracticumByPracticumSessionId(practicumSessionId);
		super.bind(object, "title", "summary", "initialDate", "endDate", "link");
		object.setPracticum(practicum);
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		Date minimumDate;

		if (!super.getBuffer().getErrors().hasErrors("initialDate")) {
			minimumDate = MomentHelper.deltaFromCurrentMoment(7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getInitialDate(), minimumDate), "initialDate", "company.practicum.error.label.initialDate");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isAfter(object.getEndDate(), object.getInitialDate()), "endDate", "company.practicum.error.label.endDate");

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isLongEnough(object.getInitialDate(), object.getEndDate(), 7, ChronoUnit.DAYS), "endDate", "company.practicum.error.label.difference");
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "initialDate", "endDate", "link");

		super.getResponse().setData(tuple);
	}

}
