
package acme.features.lecturer.course;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseType;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository						repository;

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
		final Course object;
		Date moment;

		moment = MomentHelper.getCurrentMoment();

		object = new Course();
		object.setCode("");
		object.setTitle("");
		object.setAbstractText("");
		object.setLink("");
		object.setCourseType(CourseType.BALANCED);
		object.setLecturer(this.repository.findOneLecturerByUserAccountId(super.getRequest().getPrincipal().getAccountId()));
		object.setPublished(false);

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

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

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
