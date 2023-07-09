
package acme.features.lecturer.lecture;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.entitites.lecture.LectureType;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Lecture object;
		Date moment;

		final int id = super.getRequest().getPrincipal().getAccountId();
		final Lecturer l = this.repository.findOneLecturerByUserAccountId(id);

		moment = MomentHelper.getCurrentMoment();

		object = new Lecture();
		object.setTitle("");
		object.setAbstractText("");
		object.setEstimateLearningTime(1.0);
		object.setBody("");
		object.setLecturer(l);
		object.setLink("");
		object.setPublished(false);
		object.setLectureType(LectureType.HANDS_ON);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link");
		object.setPublished(false);
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

		final SelectChoices choices = SelectChoices.from(LectureType.class, object.getLectureType());
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "isPublished");
		tuple.put("types", choices);
		super.getResponse().setData(tuple);
	}

}
