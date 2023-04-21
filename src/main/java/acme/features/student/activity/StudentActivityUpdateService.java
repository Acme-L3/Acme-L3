
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.features.student.enrolment.StudentEnrolmentRepository;
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
		boolean status;
		int activityId;
		Activity activity;

		activityId = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(activityId);
		status = activity != null && super.getRequest().getPrincipal().getAccountId() == activity.getEnrolment().getStudent().getUserAccount().getId();

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
		int activityId;
		Enrolment enrolment;

		activityId = super.getRequest().getData("id", int.class);
		enrolment = this.repo.findEnrolmentByActivityId(activityId);
		super.bind(object, "title", "summary", "activityType", "initDate", "endDate", "link");

		object.setEnrolment(enrolment);
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initDate") && !super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getInitDate(), object.getEndDate()), "initDate", "student.activity.form.error.initDate");
		super.state(MomentHelper.isBefore(object.getInitDate(), object.getEndDate()), "endDate", "student.activity.form.error.endDate");
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "summary", "activityType", "initDate", "endDate", "link");

		super.getResponse().setData(tuple);
	}
}
