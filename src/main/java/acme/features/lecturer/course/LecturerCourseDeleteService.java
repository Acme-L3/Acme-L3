
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

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
		super.bind(object, "moment", "heading", "summary", "startAvailability", "endAvailability", "price", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;
		assert object.isPublished() == false;
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "moment", "summary", "heading", "startAvailability", "endAvailability", "price", "link");
		tuple.put("published", object.isPublished());
		super.getResponse().setData(tuple);
	}
}
