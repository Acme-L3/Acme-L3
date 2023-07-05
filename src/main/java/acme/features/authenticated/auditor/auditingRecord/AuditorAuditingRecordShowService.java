
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.entitites.audits.Mark;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordShowService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditingRecordId;
		final Audit audit;

		auditingRecordId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditByAuditingRecordId(auditingRecordId);
		status = audit != null && super.getRequest().getPrincipal().hasRole(audit.getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int auditingRecordId;

		auditingRecordId = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(auditingRecordId);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		tuple.put("masterId", object.getAudit().getId());
		final SelectChoices choices = SelectChoices.from(Mark.class, object.getMark());
		tuple.put("mark", choices.getSelected().getKey());
		tuple.put("marks", choices);
		tuple.put("draftMode", object.getAudit().isDraftMode());
		super.getResponse().setData(tuple);
	}

}
