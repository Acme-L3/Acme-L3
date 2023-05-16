
package acme.features.authenticated.auditor.auditingRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		AuditingRecord auditingRecord;

		auditingRecordId = super.getRequest().getData("id", int.class);
		auditingRecord = this.repository.findAuditingRecordById(auditingRecordId);
		status = auditingRecord != null && super.getRequest().getPrincipal().hasRole(auditingRecord.getAudit().getAuditor());
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
		final String format = "dd/MM/yyyy hh:mm";

		Tuple tuple;
		tuple = super.unbind(object, "subject", "assessment", "mark", "link", "correction");
		tuple.put("initialMoment", MomentHelper.format(format, object.getInitialMoment()));
		tuple.put("finalMoment", MomentHelper.format(format, object.getFinalMoment()));
		tuple.put("hours", object.getHoursFromPeriod());
		super.getResponse().setData(tuple);
	}

}
