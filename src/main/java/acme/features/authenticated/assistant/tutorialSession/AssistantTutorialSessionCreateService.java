
package acme.features.authenticated.assistant.tutorialSession;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
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
		status = tutorial != null && super.getRequest().getPrincipal().hasRole(tutorial.getAssistant());
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
		object.setCreationMoment(moment);
		object.setStartDate(moment);
		object.setEndDate(moment);
		object.setLink("");
		object.setTutorial(tutorial);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final TutorialSession object) {
		assert object != null;
		super.bind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final TutorialSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			Duration duracion;
			final long maxDuration = 18000L;
			duracion = MomentHelper.computeDuration(object.getStartDate(), object.getEndDate());
			super.state(duracion.getSeconds() < maxDuration, "endDate", "assistant.tutorial.form.error.duration.max");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			Duration duracion;
			final long maxDuration = 86400L;
			duracion = MomentHelper.computeDuration(object.getCreationMoment(), object.getStartDate());
			super.state(duracion.getSeconds() > maxDuration, "startDate", "assistant.tutorial.form.error.one.day");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isBefore(object.getStartDate(), object.getEndDate()), "startDate", "assistant.tutorial.form.error.is.before");

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			final int tutorialId;
			final Tutorial tutorial;
			tutorialId = super.getRequest().getData("tutorialId", int.class);
			tutorial = this.repository.findTutorialById(tutorialId);
			final long minDuration = 86400L;
			Duration duracion;
			duracion = MomentHelper.computeDuration(object.getCreationMoment(), tutorial.getEndDate());
			super.state(duracion.getSeconds() > minDuration, "creationMoment", "assistant.tutorial.form.error.one.day.tutorial");
		}

		if (!super.getBuffer().getErrors().hasErrors("creationMoment")) {
			final int tutorialId;
			final Tutorial tutorial;
			tutorialId = super.getRequest().getData("tutorialId", int.class);
			tutorial = this.repository.findTutorialById(tutorialId);
			super.state(MomentHelper.isBefore(object.getCreationMoment(), tutorial.getEndDate()), "creationMoment", "assistant.tutorial.form.error.is.before.tutorial");
		}

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			final int tutorialId;
			final Tutorial tutorial;
			tutorialId = super.getRequest().getData("tutorialId", int.class);
			tutorial = this.repository.findTutorialById(tutorialId);
			super.state(MomentHelper.isBefore(tutorial.getStartDate(), object.getStartDate()), "startDate", "assistant.tutorial.form.error.is.before.tutorial2");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate")) {
			final int tutorialId;
			final Tutorial tutorial;
			tutorialId = super.getRequest().getData("tutorialId", int.class);
			tutorial = this.repository.findTutorialById(tutorialId);
			super.state(MomentHelper.isBefore(object.getEndDate(), tutorial.getEndDate()), "endDate", "assistant.tutorial.form.error.is.before.tutorial3");
		}

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
		tuple = super.unbind(object, "tittle", "summary", "creationMoment", "startDate", "endDate", "link");
		tuple.put("tutorialId", super.getRequest().getData("tutorialId", int.class));
		tuple.put("draftMode", object.getTutorial().isDraftMode());
		tuple.put("correction", false);
		super.getResponse().setData(tuple);
	}
}
