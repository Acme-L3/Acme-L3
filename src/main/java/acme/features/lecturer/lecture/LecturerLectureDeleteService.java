
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected LecturerCourseRepository	courseRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final int id = super.getRequest().getData("id", int.class);
		final Lecture l = this.repository.findLectureById(id);
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		final boolean logged = super.getRequest().getPrincipal().getAccountId() == l.getLecturer().getUserAccount().getId();
		super.getResponse().setAuthorised(status && logged);
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
	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;
		assert object.isPublished() == false;
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "course");
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		super.getResponse().setData(tuple);
	}
}
