
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.course.Course;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentDeleteService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Enrolment object;
		Principal principal;
		int enrolmentId;

		enrolmentId = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();

		status = object.getStudent().getId() == principal.getActiveRoleId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		Collection<Activity> activities;
		activities = this.repository.findActivitiesByEnrolmentId(object.getId());

		this.repository.deleteAll(activities);
		this.repository.delete(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		Course course;

		course = this.repository.findCourseById(object.getCourse().getId());

		super.bind(object, "code", "motivation", "goals");
		object.setCourse(course);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		final Collection<Course> courses;
		final SelectChoices choices;
		Tuple tuple;

		final Course course = object.getCourse();

		tuple = super.unbind(object, "code", "motivation", "goals");
		tuple.put("coursesRead", course.getCode());

		super.getResponse().setData(tuple);
	}

}
