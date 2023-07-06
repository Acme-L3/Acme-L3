
package acme.features.student.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentCourseShowService extends AbstractService<Student, Course> {

	@Autowired
	protected StudentCourseRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int id;
		Course object;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;
		int id;

		id = super.getRequest().getData("id", int.class);
		final String lecturer = object.getLecturer().getUserAccount().getUsername();

		tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "link");
		tuple.put("lecturers", lecturer);

		super.getResponse().setData(tuple);
	}

}
