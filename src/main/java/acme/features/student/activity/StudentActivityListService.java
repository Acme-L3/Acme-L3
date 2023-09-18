
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityListService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int enrolmentId;
		Enrolment enrolment;
		final Principal principal;
		Student student;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		enrolment = this.repo.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();
		student = this.repo.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && enrolment.getStudent().equals(student);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Activity> objects;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		objects = this.repo.findActivitiesByEnrolmentId(enrolmentId);

		super.getBuffer().setData(objects);
		super.getResponse().setGlobal("enrolmentId", enrolmentId);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
		int enrolmentId;

		Tuple tuple;

		enrolmentId = super.getRequest().getData("enrolmentId", int.class);
		tuple = super.unbind(object, "title", "summary", "activityType", "link");

		tuple.put("enrolmentId", enrolmentId);

		super.getResponse().setData(tuple);
	}
}
