
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseType;
import acme.entitites.lecture.Lecture;
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
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findCourseById(id);
		final boolean logged = object.getLecturer().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.getResponse().setAuthorised(status && logged && !object.isPublished());
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

		final int id = super.getRequest().getData("id", int.class);
		final Collection<Lecture> collect = this.lectureRepository.findAllLecturesByCourse(id);
		for (final Lecture l : collect)
			super.state(l.isPublished(), "courseType", "lecturer.course.form.error.lecture-not-publish");
		if (!super.getBuffer().getErrors().hasErrors("code"))
			super.state(!this.repository.existsCourseWithCodeParam(object.getCode()), "code", "lecturer.course.form.error.code-duplicate");
		if (!super.getBuffer().getErrors().hasErrors("retailPrice")) {
			super.state(object.getRetailPrice().getAmount() > 0.0, "retailPrice", "lecturer.course.form.error.retailPrice-negative");
			final boolean b = this.systemConfigurationRepository.findSystemConfiguration().get(0).getAcceptedCurrencies().contains(object.getRetailPrice().getCurrency());
			super.state(b, "retailPrice", "lecturer.course.form.error.retailPrice-not-accepted");
		}
		if (!super.getBuffer().getErrors().hasErrors("courseType"))
			super.state(object.getCourseType() != CourseType.THEORY, "courseType", "lecturer.course.form.error.courseType-theory-published");

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
		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
		tuple.put("types", choices);
		tuple.put("published", object.isPublished());
		super.getResponse().setData(tuple);
	}

}
