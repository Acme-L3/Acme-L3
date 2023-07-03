
package acme.features.authenticated.assistant.tutorialSession;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
public class AssistantTutorialSessionCreateService extends AbstractService<Assistant, TutorialSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("tutorialId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int tutorialId;
		final Tutorial tutorial;
		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final TutorialSession object;
		final int tutorialId;
		final Tutorial tutorial;

		tutorialId = super.getRequest().getData("tutorialId", int.class);
		tutorial = this.repository.findTutorialById(tutorialId);
		object = new TutorialSession();
		Date moment;
		moment = MomentHelper.getCurrentMoment();

		object.setTittle("");
		object.setSummary("");
		object.setSessionType(SessionType.HANDS_ON);
		object.setStartDate(moment);
		object.setEndDate(moment);
		object.setLink("");
		object.setTutorial(tutorial);
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

			final Calendar calendar1 = Calendar.getInstance();
			calendar1.set(Calendar.YEAR, 2000);
			calendar1.set(Calendar.MONTH, Calendar.JANUARY);
			calendar1.set(Calendar.DAY_OF_MONTH, 1);
			calendar1.set(Calendar.HOUR_OF_DAY, 00);
			calendar1.set(Calendar.MINUTE, 00);
			calendar1.set(Calendar.SECOND, 00);
			calendar1.set(Calendar.MILLISECOND, 0);
			final Date iniDate = calendar1.getTime();
			final Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, 2100);
			calendar.set(Calendar.MONTH, Calendar.DECEMBER);
			calendar.set(Calendar.DAY_OF_MONTH, 31);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 0);
			final Date limitDate = calendar.getTime();
			boolean date1;
			boolean date2;
			date1 = MomentHelper.isAfterOrEqual(object.getStartDate(), iniDate);
			date2 = MomentHelper.isBeforeOrEqual(object.getEndDate(), limitDate);
			super.state(date1 && date2, "startDate", "assistant.tutorial.form.error.limit.date");

		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Duration duracion;
			final long maxDuration = 18000L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() <= maxDuration, "endDate", "assistant.tutorial.form.error.duration.max");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long minDuration = 3600L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() >= minDuration, "startDate", "assistant.tutorial.form.error.duration.min");
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
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(SessionType.class, object.getSessionType());

		tuple = super.unbind(object, "tittle", "summary", "sessionType", "startDate", "endDate", "link");
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));
		tuple.put("choices", choices);
		super.getResponse().setData(tuple);
	}
}
