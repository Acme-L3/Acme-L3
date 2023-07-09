
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseType;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseShowService extends AbstractService<Lecturer, Course> {

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
		boolean status = false;

		final Lecturer lecturer = this.repository.findLecturerByUserAccountId(super.getRequest().getPrincipal().getAccountId());

		final int id = super.getRequest().getData("id", int.class);
		final Course course = this.repository.findCourseById(id);

		if (course != null && lecturer != null) {
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
	public void unbind(final Course object) {
		assert object != null;

		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link", "isPublished");
		tuple.put("published", object.isPublished());
		final SelectChoices choices = SelectChoices.from(CourseType.class, object.getCourseType());
		tuple.put("types", choices);
		super.getResponse().setData(tuple);
	}

}
