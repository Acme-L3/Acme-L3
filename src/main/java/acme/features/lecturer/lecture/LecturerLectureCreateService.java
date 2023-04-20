
package acme.features.lecturer.lecture;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
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
	protected AdministratorSystemConfigurationRepository	systemConfigurationRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
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
		final Lecture object;
		Date moment;

		final int id = super.getRequest().getData("id", int.class);
		final Lecture l = this.repository.findLectureById(id);

		moment = MomentHelper.getCurrentMoment();

		object = new Lecture();
		object.setTitle("");
		object.setAbstractText("");
		object.setEstimateLearningTime(0.0);
		object.setBody("");
		object.setLecturer(l.getLecturer());
		object.setLink("");

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

		if (!super.getBuffer().getErrors().hasErrors("estimateLearningTime"))
			super.state(object.getEstimateLearningTime() == 0.0, "estimateLearningTime", "lecturer.lecture.form.error.estimateLearningTime-not-zero");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "course");
		super.getResponse().setData(tuple);
	}

}
