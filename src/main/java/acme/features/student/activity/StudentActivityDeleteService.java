
package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.features.student.enrolment.StudentEnrolmentRepository;
import acme.framework.components.accounts.Principal;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentActivityDeleteService extends AbstractService<Student, Activity> {

	@Autowired
	protected StudentEnrolmentRepository repo;


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
		Principal principal;
		Enrolment object;

		id = super.getRequest().getData("id", int.class);
		activity = this.repo.findActivityById(id);

		final Enrolment enrolment = activity.getEnrolment();
		object = this.repo.findEnrolmentById(enrolment.getId());
		principal = super.getRequest().getPrincipal();

		status = object.getStudent().getId() == principal.getActiveRoleId() && activity.isDraftMode();
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
