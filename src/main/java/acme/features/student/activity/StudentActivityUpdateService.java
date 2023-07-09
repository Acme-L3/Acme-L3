
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.activities.ActivityType;
import acme.entitites.enrolments.Enrolment;
import acme.features.student.enrolment.StudentEnrolmentRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityUpdateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentEnrolmentRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		final int id;
		final Student student;
		Activity activity;
		Principal principal;
		Enrolment object;

		id = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(id);
		final Enrolment enrolment = activity.getEnrolment();
		object = this.repo.findEnrolmentById(enrolment.getId());
		principal = super.getRequest().getPrincipal();

		status = object.getStudent().getId() == principal.getActiveRoleId() && activity.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "summary", "activityType", "initDate", "endDate", "link");
	}

	@Override
	public void validate(final Activity object) {
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(object.getEndDate() != null && object.getInitDate() != null, "endDate", "student.activity.error.date");
		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isAfter(object.getEndDate(), object.getInitDate()), "endDate", "student.activity.error.endDate");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		final int enrolmentId = object.getEnrolment().getId();

		choices = SelectChoices.from(ActivityType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "summary", "activityType", "initDate", "endDate", "link", "draftMode");
		tuple.put("activities", choices);
		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}
}
