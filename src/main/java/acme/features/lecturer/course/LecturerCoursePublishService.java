
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseType;
import acme.entitites.lecture.Lecture;
import acme.entitites.lecture.LectureType;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository						repository;

	@Autowired
	protected LecturerLectureRepository						lectureRepository;

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
		super.bind(object, "code", "title", "retailPrice", "abstractText", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		final int id = super.getRequest().getData("id", int.class);
		final Collection<Lecture> collect = this.lectureRepository.findAllLecturesByCourse(id);
		final String codeInDatabase = this.repository.findCourseById(id).getCode();
		if (!super.getBuffer().getErrors().hasErrors("code") && !codeInDatabase.equalsIgnoreCase(object.getCode()))
			super.state(!this.repository.existsCourseWithCodeParam(object.getCode()), "code", "lecturer.course.form.error.code-duplicate");
		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			super.state(object.getRetailPrice().getAmount() >= 0.0, "retailPrice", "lecturer.course.form.error.retailPrice-negative");
			final boolean b = this.systemConfigurationRepository.findSystemConfiguration().get(0).getAcceptedCurrencies().contains(object.getRetailPrice().getCurrency());
			super.state(b, "retailPrice", "lecturer.course.form.error.retailPrice-not-accepted");
		}
		if (!super.getBuffer().getErrors().hasErrors("courseType")) {
			boolean purelyTheorycal = true;
			super.state(!collect.isEmpty(), "courseType", "lecturer.course.form.error.lecture-empty");
			for (final Lecture l : collect) {
				super.state(l.isPublished(), "courseType", "lecturer.course.form.error.lecture-not-publish");
				if (l.getLectureType() != LectureType.THEORY)
					purelyTheorycal = false;
			}
			super.state(!purelyTheorycal, "courseType", "lecturer.course.form.error.courseType-purely-theorycal");
		}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final SelectChoices choices = SelectChoices.from(CourseType.class, object.getCourseType());
		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link", "isPublished");
		tuple.put("types", choices);
		tuple.put("published", object.isPublished());
		super.getResponse().setData(tuple);
	}

}
