
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseLecture;
import acme.entitites.course.CourseType;
import acme.entitites.lecture.Lecture;
import acme.entitites.lecture.LectureType;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseRemoveLectureService extends AbstractService<Lecturer, Course> {

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

		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.lectureRepository.findLectureById(lectureId);
		final Collection<Lecture> lectures = this.lectureRepository.findAllLecturesByCourse(object.getId());
		if (lecture != null) {
			long handsOnLectures = lectures.stream().filter(x -> x.getLectureType().equals(LectureType.HANDS_ON)).count();
			final long totalLectures = lectures.size() - 1;
			if (lecture.getLectureType().equals(LectureType.HANDS_ON))
				handsOnLectures -= 1;

			final float ratio = (float) handsOnLectures / totalLectures;
			if (ratio < 0.4)
				object.setCourseType(CourseType.THEORY);
			else if (ratio > 0.6)
				object.setCourseType(CourseType.HANDS_ON);
			else
				object.setCourseType(CourseType.BALANCED);
		}
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		final Lecturer lecturer = this.repository.findLecturerByUserAccountId(super.getRequest().getPrincipal().getAccountId());
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.lectureRepository.findLectureById(lectureId);

		if (!super.getBuffer().getErrors().hasErrors("lecture"))
			if (lecture == null)
				super.state(lecture != null, "lecture", "lecturer.course.form.error.lecture-null");
			else
				super.state(lecture.getLecturer().getId() == lecturer.getId(), "lecture", "lecturer.course.form.error.not-your-lecture");
	}

	@Override
	public void perform(final Course course) {
		assert course != null;

		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.lectureRepository.findLectureById(lectureId);

		final CourseLecture relation = this.repository.findCourseLectureByCourseAndLecture(course.getId(), lecture.getId());

		this.repository.delete(relation);
		this.repository.save(course);
	}

	@Override
	public void unbind(final Course object) {
		final Collection<Lecture> lecturesInCourse = this.lectureRepository.findAllLecturesByCourse(object.getId());

		final SelectChoices choices = SelectChoices.from(lecturesInCourse, "title", null);

		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link", "isPublished");
		tuple.put("lectures", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		super.getResponse().setData(tuple);
	}

}
