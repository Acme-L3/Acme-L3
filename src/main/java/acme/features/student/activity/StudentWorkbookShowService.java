
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.activities.ActivityType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentWorkbookShowService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentWorkBookRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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

		super.getResponse().setData(tuple);
	}
}
