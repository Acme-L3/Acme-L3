
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseListService extends AbstractService<Authenticated, Course> {

	@Autowired
	protected AuthenticatedCourseRepository repo;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Course> courses;

		courses = this.repo.findAllPublishedCourses();

		super.getBuffer().setData(courses);
	}

	@Override
	public void unbind(final Course course) {
		assert course != null;
		Tuple tuple;

		tuple = super.unbind(course, "code", "title", "retailPrice");

		super.getResponse().setData(tuple);
	}
}
