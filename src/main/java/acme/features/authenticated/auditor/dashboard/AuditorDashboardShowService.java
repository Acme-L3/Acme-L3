
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

		final int totalNumberOfTheoryAudits;
		final int totalNumberOfHandsOnAudits;
		final int totalNumberOfBalancedAudits;
		Double averageNumberOfAuditingRecords;
		Double deviationNumberOfAuditingRecords;
		Double minimumNumberOfAuditingRecords;
		Double maximumNumberOfAuditingRecords;
		Double averageTimeAuditingRecords;
		Double timeDeviationAuditingRecords;
		Double minTimeAuditingRecords;
		Double maxTimeAuditingRecords;

		totalNumberOfTheoryAudits = this.repository.totalNumberOfTheoryAudits(id);
		totalNumberOfHandsOnAudits = this.repository.totalNumberOfHandsOnAudits(id);
		totalNumberOfBalancedAudits = this.repository.totalNumberOfBalancedAudits(id);
		averageNumberOfAuditingRecords = this.repository.averageNumberOfAuditingRecords(id);
		minimumNumberOfAuditingRecords = this.repository.minimumNumberOfAuditingRecords(id);
		maximumNumberOfAuditingRecords = this.repository.maximumNumberOfAuditingRecords(id);
		deviationNumberOfAuditingRecords = this.repository.deviationOfAuditingRecords(id);
		averageTimeAuditingRecords = this.repository.averageTimeOfAuditingRecords(id);
		timeDeviationAuditingRecords = this.repository.timeDeviationOfAuditingRecords(id);
		minTimeAuditingRecords = this.repository.minimumTimeOfAuditingRecords(id);
		maxTimeAuditingRecords = this.repository.maximumTimeOfAuditingRecords(id);

		final AuditorDashboard dashboard = new AuditorDashboard();
		dashboard.setTotalNumberOfTheoryAudits(totalNumberOfTheoryAudits);
		dashboard.setTotalNumberOfHandsOnAudits(totalNumberOfHandsOnAudits);
		dashboard.setTotalNumberOfBalancedAudits(totalNumberOfBalancedAudits);
		dashboard.setAverageNumberOfAuditingRecords(averageNumberOfAuditingRecords);
		dashboard.setMinimumNumberOfAuditingRecords(minimumNumberOfAuditingRecords);
		dashboard.setMaximumNumberOfAuditingRecords(maximumNumberOfAuditingRecords);
		dashboard.setDeviationNumberOfAuditingRecords(deviationNumberOfAuditingRecords);
		dashboard.setAverageTimeAuditingRecords(averageTimeAuditingRecords);
		dashboard.setTimeDeviationAuditingRecords(timeDeviationAuditingRecords);
		dashboard.setMinTimeAuditingRecords(minTimeAuditingRecords);
		dashboard.setMaxTimeAuditingRecords(maxTimeAuditingRecords);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberOfTheoryAudits", "totalNumberOfHandsOnAudits", "totalNumberOfBalancedAudits", "averageNumberOfAuditingRecords", "minimumNumberOfAuditingRecords", "maximumNumberOfAuditingRecords",
			"deviationNumberOfAuditingRecords", "averageTimeAuditingRecords", "timeDeviationAuditingRecords", "minTimeAuditingRecords", "maxTimeAuditingRecords");

		super.getResponse().setData(tuple);
	}

}
