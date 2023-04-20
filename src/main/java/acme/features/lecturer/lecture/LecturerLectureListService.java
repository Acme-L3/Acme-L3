
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		final boolean status2 = super.getRequest().hasData("courseId", int.class);
		super.getResponse().setAuthorised(status && status2);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		final int id = super.getRequest().getData("courseId", int.class);
		objects = this.repository.findAllLecturesByCourse(id);

		super.getBuffer().setData(objects);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "title", "abstractText", "estimateLearningTime", "body", "lectureType", "link", "course");
		tuple.put("courseId", super.getRequest().getData("courseId", int.class));
		super.getResponse().setData(tuple);
	}

}
