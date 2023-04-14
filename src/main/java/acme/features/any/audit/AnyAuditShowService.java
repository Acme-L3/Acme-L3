
package acme.features.any.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.course.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AnyAuditShowService extends AbstractService<Any, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Audit object = this.repository.findAuditById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		final Collection<Course> courses;
		final Collection<Auditor> auditors;
		final SelectChoices coursesChoices;
		final SelectChoices auditorsChoices;

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());

		auditors = this.repository.findAllAuditors();
		auditorsChoices = SelectChoices.from(auditors, "professionalID", object.getAuditor());

		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);
		tuple.put("auditor", auditorsChoices.getSelected().getKey());
		tuple.put("auditors", auditorsChoices);

		super.getResponse().setData(tuple);
	}
}
