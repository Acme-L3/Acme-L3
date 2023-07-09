
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.entitites.lecture.LectureType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

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
		super.getResponse().setAuthorised(status && logged);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Lecture object = this.repository.findLectureById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "isPublished");
		tuple.put("published", object.isPublished());
		final SelectChoices choices = SelectChoices.from(LectureType.class, object.getLectureType());
		tuple.put("types", choices);
		super.getResponse().setData(tuple);
	}

}
