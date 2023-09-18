
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.activities.ActivityType;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Activity activity;
		final Principal principal;
		Student student;

		id = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(id);
		principal = super.getRequest().getPrincipal();
		student = this.repo.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && activity.getEnrolment().getStudent().equals(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int activityId;
		Activity activity;

		activityId = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(activityId);

		super.getBuffer().setData(activity);
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
		tuple.put("draftMode", object.isDraftMode());

		super.getResponse().setData(tuple);
	}
}
