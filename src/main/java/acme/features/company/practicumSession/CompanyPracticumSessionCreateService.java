
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
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


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
		final PracticumSession object;
		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(practicumId);
		object = new PracticumSession();
		object.setPracticum(practicum);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final PracticumSession object) {
		assert object != null;

		int practicumId;
		Practicum practicum;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(practicumId);

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

		int practicumId;
		Practicum practicum;
		Tuple tuple;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(practicumId);

		tuple = super.unbind(object, "title", "summary", "initialDate", "endDate", "link");
		tuple.put("practicum", practicum);
		tuple.put("id", practicumId);

		super.getResponse().setData(tuple);
	}
}
