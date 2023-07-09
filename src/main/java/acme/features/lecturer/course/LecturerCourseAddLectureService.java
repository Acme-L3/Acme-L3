
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
public class LecturerCourseAddLectureService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	protected LecturerLectureRepository	lectureRepository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

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
		final Collection<Lecture> collection = this.lectureRepository.findAllLecturesByCourse(object.getId());
		if (lecture != null) {
			long handsOnLectures = collection.stream().filter(x -> x.getLectureType().equals(LectureType.HANDS_ON)).count();
			final int totalLectures = collection.size() + 1;
			if (lecture.getLectureType().equals(LectureType.HANDS_ON))
				handsOnLectures += 1;

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
	public void perform(final Course object) {
		final int lectureId = super.getRequest().getData("lecture", int.class);
		final Lecture lecture = this.lectureRepository.findLectureById(lectureId);

		final CourseLecture relation = new CourseLecture();
		relation.setCourse(object);
		relation.setLecture(lecture);

		this.repository.save(object);
		this.repository.save(relation);
	}

	@Override
	public void unbind(final Course object) {

		final int lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		final Collection<Lecture> lectures = this.lectureRepository.findAllLecturesByLecturerId(lecturerId);
		lectures.removeIf(x -> !x.isPublished());
		final Collection<Lecture> lecturesInCourse = this.lectureRepository.findAllLecturesByCourse(object.getId());
		lectures.removeAll(lecturesInCourse);

		final SelectChoices choices = SelectChoices.from(lectures, "title", null);

		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
		tuple.put("lectures", choices.getSelected().getKey());
		tuple.put("lectures", choices);

		super.getResponse().setData(tuple);
	}

}
