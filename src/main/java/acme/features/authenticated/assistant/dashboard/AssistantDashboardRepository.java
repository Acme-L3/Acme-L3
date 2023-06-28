
package acme.features.authenticated.assistant.dashboard;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	@Query("select a from Assistant a where a.userAccount.id = :accountId")
	Assistant findAssistantByAccountId(int accountId);

	@Query("select count(t) from Tutorial t where t.assistant.id = :id")
	Double totalNumberOfTutorials(int id);

	@Query("select au from TutorialSession au where au.tutorial.assistant.id = :id")
	List<TutorialSession> findAllTutorialSessionsByAssistantId(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	List<Tutorial> findAllTutorialsByAssistantId(int id);

	default Double deviationTimeTutorial(final int id) {
		final List<Tutorial> records = this.findAllTutorialsByAssistantId(id);
		final List<Double> hours = records.stream().map(Tutorial::getHoursFromPeriod).collect(Collectors.toList());
		final Double average = hours.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = hours.stream().map(h -> Math.pow(h - average, 2)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double averageTimeOfTutorials(final int id) {
		final List<Tutorial> records = this.findAllTutorialsByAssistantId(id);
		return records.stream().mapToDouble(Tutorial::getHoursFromPeriod).average().orElse(0);
	}

	default Double averageTimeOfTutorialSessions(final int id) {
		final List<TutorialSession> records = this.findAllTutorialSessionsByAssistantId(id);
		return records.stream().mapToDouble(TutorialSession::getHoursFromPeriod).average().orElse(0);
	}

	default Double deviationTimeTutorialSession(final int id) {
		final List<TutorialSession> records = this.findAllTutorialSessionsByAssistantId(id);
		final List<Double> hours = records.stream().map(TutorialSession::getHoursFromPeriod).collect(Collectors.toList());
		final Double average = hours.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		final List<Double> squaredDistancesToMean = hours.stream().map(h -> Math.pow(h - average, 2)).collect(Collectors.toList());
		final Double averageSquaredDistancesToMean = squaredDistancesToMean.stream().mapToDouble(Double::doubleValue).average().orElse(0);
		return Math.sqrt(averageSquaredDistancesToMean);
	}

	default Double minimumTimeOfTutorial(final int id) {
		final List<Tutorial> records = this.findAllTutorialsByAssistantId(id);
		return records.stream().mapToDouble(Tutorial::getHoursFromPeriod).min().orElse(0);
	}

	default Double maximumTimeOfTutorial(final int id) {
		final List<Tutorial> records = this.findAllTutorialsByAssistantId(id);
		return records.stream().mapToDouble(Tutorial::getHoursFromPeriod).max().orElse(0);
	}

	default Double minimumTimeOfTutorialSession(final int id) {
		final List<TutorialSession> records = this.findAllTutorialSessionsByAssistantId(id);
		return records.stream().mapToDouble(TutorialSession::getHoursFromPeriod).min().orElse(0);
	}

	default Double maximumTimeOfTutorialSession(final int id) {
		final List<TutorialSession> records = this.findAllTutorialSessionsByAssistantId(id);
		return records.stream().mapToDouble(TutorialSession::getHoursFromPeriod).max().orElse(0);
	}

}
