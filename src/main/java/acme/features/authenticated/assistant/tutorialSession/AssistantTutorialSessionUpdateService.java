
package acme.features.authenticated.assistant.tutorialSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.SessionType;
import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialSessionUpdateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int tutorialSessionId;
		Tutorial tutorial;
		Assistant assistant;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByTutorialSessionId(tutorialSessionId);
		assistant = tutorial == null ? null : tutorial.getAssistant();
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(assistant);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TutorialSession object;
		final int tutorialSessionId;

		tutorialSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findTutorialSessionById(tutorialSessionId);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "tittle", "summary", "sessionType", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			boolean oneDay;

			oneDay = MomentHelper.isAfter(object.getStartDate(), MomentHelper.deltaFromCurrentMoment(1l, ChronoUnit.DAYS));

			super.state(oneDay, "startDate", "assistant.tutorial.form.error.one.day.ahead");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long maxDuration = 18000L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() < maxDuration, "startDate", "assistant.tutorial.form.error.duration.max");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long minDuration = 3600L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() > minDuration, "startDate", "assistant.tutorial.form.error.duration.min");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {

			final Date iniDate = new Date(946681200000L);
			final Date limitDate = new Date(4102449540000L);
			boolean date1;
			boolean date2;
			date1 = MomentHelper.isAfterOrEqual(object.getStartDate(), iniDate);
			date2 = MomentHelper.isBeforeOrEqual(object.getStartDate(), limitDate);
			super.state(date1 && date2, "startDate", "assistant.tutorial.form.error.limit.date");

		}

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "assistant.tutorial.form.error.is.before");

	}

	@Override
	public void perform(final TutorialSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TutorialSession object) {
		assert object != null;

		SelectChoices choices;

		choices = SelectChoices.from(SessionType.class, object.getSessionType());

		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "sessionType", "startDate", "endDate", "link");
		tuple.put("id", object.getTutorial().getId());
		tuple.put("choices", choices);
		tuple.put("draftMode", object.getTutorial().isDraftMode());
	}
}
