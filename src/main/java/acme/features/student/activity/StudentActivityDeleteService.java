
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.framework.components.accounts.Principal;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentActivityRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Activity activity;
		final Principal principal;
		Student student;

		id = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(id);
		principal = super.getRequest().getPrincipal();
		student = this.repo.findStudentByPrincipalId(principal.getActiveRoleId());
		status = student != null && activity.getEnrolment().getStudent().equals(student) && !activity.getEnrolment().isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Activity object;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "summary", "activityType", "initDate", "endDate", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repo.delete(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;
	}

}
