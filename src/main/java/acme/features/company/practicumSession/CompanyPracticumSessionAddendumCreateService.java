
package acme.features.company.practicumSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.entitites.session.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionAddendumCreateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int practicumId;
		Practicum practicum;
		boolean addendumCheck;

		practicumId = super.getRequest().getData("masterId", int.class);
		addendumCheck = this.repo.findAddendumByPracticumId(true, practicumId) != null;
		practicum = this.repo.findPracticumById(practicumId);
		status = !addendumCheck && practicum != null && !practicum.getDraftMode() && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int practicumId;
		Practicum practicum;
		PracticumSession object;

		practicumId = this.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(practicumId);

		object = new PracticumSession();
		object.setPracticum(practicum);
		object.setAddendum(true);

		super.getResponse().setGlobal("draftMode", practicum.getDraftMode());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {

		assert object != null;

		super.bind(object, "title", "summary", "initialDate", "endDate", "link");
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

		if (!object.getPracticum().getDraftMode())
			super.state(super.getRequest().getData("check", boolean.class), "check", "company.practicum.error.label.check");
	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		int masterId;
		Tuple tuple;

		masterId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(object, "title", "summary", "initialDate", "endDate", "link");
		tuple.put("masterId", masterId);

		super.getResponse().setData(tuple);
	}

}
