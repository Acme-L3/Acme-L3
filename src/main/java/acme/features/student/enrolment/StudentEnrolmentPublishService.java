
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentPublishService extends AbstractService<Student, Enrolment> {

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
		int enrolmentId;
		Enrolment enrolment;
		Principal principal;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(enrolmentId);
		principal = super.getRequest().getPrincipal();

		status = enrolment.getStudent().getId() == principal.getActiveRoleId();

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
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "holderName", "lowerNibble");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("holderName")) {
			String holderName;
			holderName = object.getHolderName();

			super.state(holderName.length() != 0, "holderName", "student.enrolment.error.holderName.null");
		}

		if (!super.getBuffer().getErrors().hasErrors("lowerNibble")) {
			String lowerNibble;
			lowerNibble = object.getLowerNibble();

			super.state(lowerNibble.length() != 0, "lowerNibble", "student.enrolment.error.lowerNibble.null");
			super.state(lowerNibble.length() == 16, "lowerNibble", "student.enrolment.error.lowerNibble.notValidNumber");
		}

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);

		final String lowerNibble = super.getRequest().getData("lowerNibble", String.class);
		object.setLowerNibble(lowerNibble.substring(12, 16));

		final String holderName = super.getRequest().getData("holderName", String.class);
		object.setHolderName(holderName);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Collection<Course> courses;
		SelectChoices choices;

		final int id = object.getStudent().getId();

		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "code", object.getCourse());

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "lowerNibble", "holderName", "draftMode");
		tuple.put("courses", choices);

		super.getResponse().setData(tuple);
	}

}
