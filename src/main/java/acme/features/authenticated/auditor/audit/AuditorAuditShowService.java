
package acme.features.authenticated.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.Mark;
import acme.entitites.course.Course;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditId;
		Audit audit;

		auditId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditById(auditId);
		status = audit != null && super.getRequest().getPrincipal().getActiveRoleId() == audit.getAuditor().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		final Collection<Course> courses;
		final SelectChoices coursesChoices;
		final Tuple tuple;

		final Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		String mark;
		if (marks.isEmpty())
			mark = "Vacía";
		else
			mark = object.calculateMark(marks).toString();

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "Auditor", "course", "draftMode");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);
		tuple.put("mark", mark);

		super.getResponse().setData(tuple);
	}

}
