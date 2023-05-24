
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

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
		final boolean status;
		int auditingRecordId;
		Audit audit;

		auditingRecordId = super.getRequest().getData("id", int.class);
		audit = this.repository.findAuditByAuditingRecordId(auditingRecordId);
		status = audit != null && audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
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
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("initialMoment") && !super.getBuffer().getErrors().hasErrors("finalMoment"))
			if (!MomentHelper.isBefore(object.getInitialMoment(), object.getFinalMoment()))
				super.state(false, "initialMoment", "auditor.auditingrecord.error.date.initialAfterFinal");
			else
				super.state(!(object.getHoursFromPeriod() < 1), "finalMoment", "auditor.auditingrecord.error.date.shortPeriod");
	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		tuple.put("auditId", object.getAudit().getId());
		tuple.put("draftMode", object.getAudit().isDraftMode());
		super.getResponse().setData(tuple);
	}

}
