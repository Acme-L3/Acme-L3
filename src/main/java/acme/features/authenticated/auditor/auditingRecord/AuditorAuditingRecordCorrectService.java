
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.controllers.HttpMethod;
import acme.framework.helpers.MomentHelper;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordCorrectService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("auditId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Audit audit = this.repository.findAuditById(super.getRequest().getData("auditId", int.class));
		status = super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && !audit.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final AuditingRecord auditingRecord = new AuditingRecord();

		super.getBuffer().setData(auditingRecord);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;

		final int auditId = super.getRequest().getData("auditId", int.class);
		final Audit audit = this.repository.findAuditById(auditId);

		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		object.setAudit(audit);
		object.setDraftMode(false);
		object.setCorrection(true);
	}

	@Override
	public void validate(final AuditingRecord object) {
		assert object != null;

		final Boolean correction = super.getRequest().getData("correction", boolean.class);
		super.state(correction, "*", "auditor.auditingrecord.correction.confirmation");

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
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));
		super.getResponse().setData(tuple);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
