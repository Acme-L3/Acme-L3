
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.lecture.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureListService extends AbstractService<Student, Lecture> {

	@Autowired
	protected StudentLectureRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> object;

		final Integer courseId = super.getRequest().getData("id", int.class);
		object = this.repo.findLectureByCourseId(courseId);

		super.getBuffer().setData(object);

	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "estimateLearningTime");

		super.getResponse().setData(tuple);
	}
}
