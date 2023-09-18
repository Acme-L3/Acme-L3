
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.activities.ActivityType;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityCreateService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("enrolmentId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		Principal principal;
		Student student;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repo.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();
		student = this.repo.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && enrolment.getStudent().equals(student) && !enrolment.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Activity object;
		Enrolment enrolment;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repo.findEnrolmentById(enrolmentId);

		object = new Activity();
		object.setEnrolment(enrolment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		int enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repo.findEnrolmentById(enrolmentId);

		super.bind(object, "title", "summary", "activityType", "initDate", "endDate", "link");
		object.setEnrolment(enrolment);
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

		object.setDraftMode(true);

		this.repo.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		final int enrolmentId = object.getEnrolment().getId();

		choices = SelectChoices.from(ActivityType.class, object.getActivityType());

		tuple = super.unbind(object, "title", "summary", "activityType", "initDate", "endDate", "link");
		tuple.put("activities", choices);
		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}

}
