
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentCreateService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repo;


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
		Enrolment object;
		Student student;
		int studentId;

		studentId = super.getRequest().getPrincipal().getActiveRoleId();
		student = this.repo.findStudentById(studentId);

		object = new Enrolment();
		object.setStudent(student);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		int courseId;
		Course course;

		courseId = super.getRequest().getData("courses", int.class);
		course = this.repo.findCourseById(courseId);

		object.setCourse(course);
		object.setDraftMode(true);
		super.bind(object, "code", "motivation", "goals");
	}

	@Override
	public void validate(final Enrolment object) {
		final Collection<String> allCodes = this.repo.findAllCodesFromEnrolments();
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(!allCodes.contains(object.getCode()), "code", "student.enrolment.error.code");
		if (!super.getBuffer().getErrors().hasErrors("courses"))
			super.state(object.getCourse() != null, "courses", "student.enrolment.error.course");

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		this.repo.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;
		Tuple tuple;

		courses = this.repo.findAllPublishedCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		tuple = super.unbind(object, "code", "motivation", "goals", "course");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);

	}

}
