
package acme.features.authenticated.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.audits.Audit;
import acme.entitites.audits.AuditingRecord;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordListService extends AbstractService<Auditor, AuditingRecord> {

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
		final int auditId;
		final Audit audit;
		Auditor loggedAuditor;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);
		loggedAuditor = this.repository.findOneAuditorByUsername(super.getRequest().getPrincipal().getUsername());
		status = audit != null && loggedAuditor == audit.getAuditor();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<AuditingRecord> objects;
		int auditId;

		auditId = super.getRequest().getData("auditId", int.class);
		objects = this.repository.findAuditingRecordsByAuditId(auditId);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "initialMoment", "finalMoment", "mark", "link", "correction");
		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<AuditingRecord> objects) {
		assert objects != null;
		int auditId;
		Audit audit;
		boolean showCreate;

		auditId = super.getRequest().getData("auditId", int.class);
		audit = this.repository.findAuditById(auditId);
		showCreate = audit.isDraftMode() && super.getRequest().getPrincipal().hasRole(audit.getAuditor());
		super.getResponse().setGlobal("auditId", auditId);
		super.getResponse().setGlobal("showCreate", showCreate);
	}
}
