
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository	repository;
	@Autowired
	protected LecturerLectureRepository	lectureRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status = false;

		final Lecturer lecturer = this.repository.findLecturerByUserAccountId(super.getRequest().getPrincipal().getAccountId());

		final int id = super.getRequest().getData("id", int.class);
		final Course course = this.repository.findCourseById(id);

		if (course != null && !course.isPublished() && lecturer != null) {
			final Collection<Course> lecturerCourses = this.repository.findAllCoursesByLecturerId(lecturer.getId());
			status = lecturerCourses.contains(course);
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findCourseById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		assert object.isPublished() == false;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link", "isPublished");
		super.getResponse().setData(tuple);
	}
}
