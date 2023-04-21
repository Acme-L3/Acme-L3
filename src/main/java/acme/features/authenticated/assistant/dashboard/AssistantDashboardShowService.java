
package acme.features.authenticated.assistant.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.AssistantDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantDashboardShowService extends AbstractService<Assistant, AssistantDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(super.getRequest().getPrincipal().hasRole(Assistant.class));
	}

	@Override
	public void load() {

		final int id = this.repository.findAssistantByAccountId(super.getRequest().getPrincipal().getAccountId()).getId();

		AssistantDashboard dashboard;
		final Double totalNumberOfTutorials;
		final Double averageTimeOfTutorials;
		final Double deviationTimeTutorial;
		final Double minimumTimeOfTutorial;
		final Double maximumTimeOfTutorial;
		final Double averageTimeOfTutorialSessions;
		final Double deviationTimeSession;
		final Double minimumTimeOfTutorialSession;
		final Double maximumTimeOfTutorialSession;

		totalNumberOfTutorials = this.repository.totalNumberOfTutorials(id);
		averageTimeOfTutorials = this.repository.averageTimeOfTutorials(id);
		deviationTimeTutorial = this.repository.deviationTimeTutorial(id);
		minimumTimeOfTutorial = this.repository.minimumTimeOfTutorial(id);
		maximumTimeOfTutorial = this.repository.maximumTimeOfTutorial(id);
		averageTimeOfTutorialSessions = this.repository.averageTimeOfTutorialSessions(id);
		deviationTimeSession = this.repository.deviationTimeSession(id);
		minimumTimeOfTutorialSession = this.repository.minimumTimeOfTutorialSession(id);
		maximumTimeOfTutorialSession = this.repository.maximumTimeOfTutorialSession(id);

		dashboard = new AssistantDashboard();
		dashboard.setTotalNumberOfTutorials(totalNumberOfTutorials);
		dashboard.setAverageTimePerSession(averageTimeOfTutorialSessions);
		dashboard.setDeviationTimeSession(deviationTimeSession);
		dashboard.setMinTimeSession(minimumTimeOfTutorialSession);
		dashboard.setMaxTimeSession(maximumTimeOfTutorialSession);
		dashboard.setAverageTimePerTutorial(averageTimeOfTutorials);
		dashboard.setDeviationTimeTutorial(deviationTimeTutorial);
		dashboard.setMinTimeTutorial(minimumTimeOfTutorial);
		dashboard.setMaxTimeTutorial(maximumTimeOfTutorial);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AssistantDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, "totalNumberOfTutorials", "averageTimePerSession", "deviationTimeSession", "minTimeSession", "maxTimeSession", "averageTimePerTutorial", "deviationTimeTutorial", "minTimeTutorial", "maxTimeTutorial");
		super.getResponse().setData(tuple);
	}
}
