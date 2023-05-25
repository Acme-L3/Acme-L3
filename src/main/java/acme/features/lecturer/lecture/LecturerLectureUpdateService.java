
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.entitites.lecture.LectureType;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository						repository;

	@Autowired
	protected LecturerCourseRepository						courseRepository;

	@Autowired
	protected AdministratorSystemConfigurationRepository	systemConfigurationRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		final int id = super.getRequest().getData("id", int.class);
		final Lecture object = this.repository.findLectureById(id);
		final boolean logged = object.getLecturer().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.getResponse().setAuthorised(status && logged && !object.isPublished());
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Lecture object = this.repository.findLectureById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "course");
		final int id = super.getRequest().getData("id", int.class);
		object.setCourse(this.courseRepository.findCourseById(this.repository.findLectureById(id).getCourse().getId()));
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("estimateLearningTime"))
			super.state(object.getEstimateLearningTime() > 0.0, "estimateLearningTime", "lecturer.lecture.form.error.estimateLearningTime-not-zero");

	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final int id = super.getRequest().getData("id", int.class);
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "course");
		final SelectChoices choices = SelectChoices.from(LectureType.class, object.getLectureType());
		tuple.put("types", choices);
		tuple.put("published", object.isPublished());
		tuple.put("coursePublished", object.getCourse().isPublished());
		tuple.put("courseId", this.repository.findLectureById(id).getCourse().getId());
		super.getResponse().setData(tuple);
	}

}
