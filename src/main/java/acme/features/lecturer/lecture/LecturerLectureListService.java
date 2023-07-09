
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.lecture.Lecture;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	protected LecturerCourseRepository	courseRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		final Principal principal = super.getRequest().getPrincipal();
		objects = this.repository.findAllLecturesByLecturerId(principal.getActiveRoleId());

		if (super.getRequest().hasData("courseId")) {
			final int courseId = super.getRequest().getData("courseId", int.class);

			final Course course = this.courseRepository.findCourseById(courseId);
			if (course != null)
				objects = this.repository.findLecturesByCourseAndLecturer(courseId, principal.getActiveRoleId());
		}

		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link");

		super.getResponse().setData(tuple);

	}

}
