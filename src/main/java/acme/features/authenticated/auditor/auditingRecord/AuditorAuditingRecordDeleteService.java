
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordDeleteService extends AbstractService<Auditor, AuditingRecord> {

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
		AuditingRecord auditingRecord;

		auditingRecordId = super.getRequest().getData("id", int.class);
		auditingRecord = this.repository.findAuditingRecordById(auditingRecordId);
		status = auditingRecord != null && super.getRequest().getPrincipal().hasRole(auditingRecord.getAudit().getAuditor()) && auditingRecord.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditingRecordById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		int auditingRecordId;
		final Audit audit;

		auditingRecordId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditByAuditingRecordId(auditingRecordId);
		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		object.setAudit(audit);

	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;

		int id;

		id = super.getRequest().getData("id", int.class);
		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		tuple.put("auditId", this.repository.findAuditingRecordById(id).getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());

		super.getResponse().setData(tuple);
	}

}
