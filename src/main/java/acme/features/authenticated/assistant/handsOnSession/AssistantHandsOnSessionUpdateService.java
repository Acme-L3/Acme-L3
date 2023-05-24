
package acme.features.authenticated.assistant.handsOnSession;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.HandsOnSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantHandsOnSessionUpdateService extends AbstractService<Assistant, HandsOnSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status;
		int handsOnSessionId;
		Tutorial tutorial;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
		status = tutorial != null && tutorial.isDraftMode() && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		HandsOnSession object;
		final int handsOnSessionId;

		handsOnSessionId = super.getRequest().getData("id", int.class);
		object = this.repository.findHandsOnSessionById(handsOnSessionId);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final HandsOnSession object) {
		assert object != null;
		super.bind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final HandsOnSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long maxDuration = 18000L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() <= maxDuration, "startDate", "assistant.tutorial.form.error.duration.max");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long minDuration = 3600L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() >= minDuration, "startDate", "assistant.tutorial.form.error.duration.min");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long maxDuration = 86400L;
			duracion = MomentHelper.computeDuration(object.getCreationMoment(), object.getStartDate());
			super.state(duracion.getSeconds() >= maxDuration, "startDate", "assistant.tutorial.form.error.one.day");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "assistant.tutorial.form.error.is.before");

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			int handsOnSessionId;
			Tutorial tutorial;
			handsOnSessionId = super.getRequest().getData("id", int.class);
			tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
			final long minDuration = 86400L;
			Duration duracion;
			duracion = MomentHelper.computeDuration(object.getCreationMoment(), tutorial.getEndDate());
			super.state(duracion.getSeconds() > minDuration, "creationMoment", "assistant.tutorial.form.error.one.day.tutorial");
		}

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			int handsOnSessionId;
			Tutorial tutorial;
			handsOnSessionId = super.getRequest().getData("id", int.class);
			tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
			super.state(MomentHelper.isBeforeOrEqual(object.getCreationMoment(), tutorial.getEndDate()), "creationMoment", "assistant.tutorial.form.error.is.before.tutorial");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			int handsOnSessionId;
			Tutorial tutorial;
			handsOnSessionId = super.getRequest().getData("id", int.class);
			tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
			super.state(MomentHelper.isBeforeOrEqual(tutorial.getStartDate(), object.getStartDate()), "startDate", "assistant.tutorial.form.error.is.before.tutorial2");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			int handsOnSessionId;
			Tutorial tutorial;
			handsOnSessionId = super.getRequest().getData("id", int.class);
			tutorial = this.repository.findTutorialByHandsOnSessionId(handsOnSessionId);
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDate(), tutorial.getEndDate()), "endDate", "assistant.tutorial.form.error.is.before.tutorial3");
		}

	}

	@Override
	public void perform(final HandsOnSession object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final HandsOnSession object) {
		assert object != null;
		Tuple tuple;
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		tuple.put("id", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		super.getResponse().setData(tuple);
	}
}
