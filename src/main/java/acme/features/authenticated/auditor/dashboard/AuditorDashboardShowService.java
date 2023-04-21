
package acme.features.authenticated.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Auditor.class));
	}

	@Override
	public void load() {

		final int id = this.repository.findAuditorByAccountId(super.getRequest().getPrincipal().getAccountId()).getId();

		AuditorDashboard dashboard;
		Double totalNumberAudits;
		Double averageAuditingRecords;
		Double deviationAuditingRecords;
		Double minAuditingRecords;
		Double maxAuditingRecords;
		Double averageTimeAuditingRecords;
		Double timeDeviationAuditingRecords;
		Double minTimeAuditingRecords;
		Double maxTimeAuditingRecords;

		totalNumberAudits = this.repository.totalNumberOfAudits(id);
		averageAuditingRecords = this.repository.averageNumberOfAuditingRecords(id);
		minAuditingRecords = this.repository.minimumNumberOfAuditingRecords(id);
		maxAuditingRecords = this.repository.maximumNumberOfAuditingRecords(id);
		deviationAuditingRecords = this.repository.deviationOfAuditingRecords(id);
		averageTimeAuditingRecords = this.repository.averageTimeOfAuditingRecords(id);
		timeDeviationAuditingRecords = this.repository.timeDeviationOfAuditingRecords(id);
		minTimeAuditingRecords = this.repository.minimumTimeOfAuditingRecords(id);
		maxTimeAuditingRecords = this.repository.maximumTimeOfAuditingRecords(id);

		dashboard = new AuditorDashboard();
		dashboard.setTotalNumberAudits(totalNumberAudits);
		dashboard.setAverageAuditingRecords(averageAuditingRecords);
		dashboard.setMinAuditingRecords(minAuditingRecords);
		dashboard.setMaxAuditingRecords(maxAuditingRecords);
		dashboard.setDeviationAuditingRecords(deviationAuditingRecords);
		dashboard.setAverageTimeAuditingRecords(averageTimeAuditingRecords);
		dashboard.setTimeDeviationAuditingRecords(timeDeviationAuditingRecords);
		dashboard.setMinTimeAuditingRecords(minTimeAuditingRecords);
		dashboard.setMaxTimeAuditingRecords(maxTimeAuditingRecords);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberAudits", "averageAuditingRecords", "minAuditingRecords", "maxAuditingRecords", "deviationAuditingRecords", "averageTimeAuditingRecords", "timeDeviationAuditingRecords", "minTimeAuditingRecords",
			"maxTimeAuditingRecords");

		super.getResponse().setData(tuple);
	}

}
