
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordPublishService extends AbstractService<Auditor, AuditingRecord> {

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
		status = super.getRequest().getPrincipal().hasRole(auditingRecord.getAudit().getAuditor()) && auditingRecord.isDraftMode();
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
		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "link", "draftMode");
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
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;

		final Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link", "draftMode");

		super.getResponse().setData(tuple);
	}

}
