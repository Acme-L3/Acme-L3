
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
public class AuditorAuditingRecordCreateService extends AbstractService<Auditor, AuditingRecord> {

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
		final boolean status;
		int auditId;
		final Audit audit;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);
		status = super.getRequest().getPrincipal().hasRole(audit.getAuditor()) && audit.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		//		final AuditingRecord object;
		//		int auditId;
		//		Audit audit;
		//
		//		auditId = super.getRequest().getData("auditId", int.class);
		//		audit = this.repository.findAuditById(auditId);
		//		object = new AuditingRecord();
		//		object.setSubject("");
		//		object.setAssessment("");
		//		object.setInitialMoment(null);
		//		object.setFinalMoment(null);
		//		object.setMark("");
		//		object.setLink("");
		//		object.setCorrection(false);
		//		object.setAudit(audit);
		//		super.getBuffer().setData(object);

		AuditingRecord object;

		object = new AuditingRecord();
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		//		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		//		object.setCorrection(false);

		final int auditId = super.getRequest().getData("auditId", int.class);
		final Audit audit = this.repository.findAuditById(auditId);
		super.bind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link");
		object.setAudit(audit);
		object.setDraftMode(true);
		object.setCorrection(false);
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
		tuple.put("auditId", super.getRequest().getData("auditId", int.class));
		super.getResponse().setData(tuple);

	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals(HttpMethod.POST))
			PrincipalHelper.handleUpdate();
	}

}
