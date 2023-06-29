
package acme.features.authenticated.assistant.tutorial;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


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
		final Tutorial object;
		Date moment;
		final Calendar calendar1 = Calendar.getInstance();
		final Calendar calendar2 = Calendar.getInstance();
		final Date moment1;
		final Date moment2;
		final Assistant assistant;

		moment = MomentHelper.getCurrentMoment();
		calendar1.setTime(moment);
		calendar1.add(Calendar.HOUR, 1);
		moment1 = calendar1.getTime();
		calendar2.setTime(moment);
		calendar2.add(Calendar.HOUR, 2);
		moment2 = calendar1.getTime();

		assistant = this.repository.findAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Tutorial();
		object.setDraftMode(true);
		object.setAssistant(assistant);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		int courseId;
		Course course;

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		super.bind(object, "code", "tittle", "summary", "goals", "startDate", "endDate", "draftMode");
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Tutorial existing;
			existing = this.repository.findTutorialByCode(object.getCode());
			super.state(existing == null || existing.getId() == object.getId(), "code", "assistant.tutorial.form.error.duplicated-code");
		}

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;
		Collection<Course> courses;
		SelectChoices coursesChoices;
		Tuple tuple;

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = this.unbind(object, "code", "tittle", "summary", "goals", "startDate", "endDate", "draftMode");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);
		super.getResponse().setData(tuple);
	}
}
