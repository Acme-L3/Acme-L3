
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.session.TutorialSession;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialShowService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		Tutorial tutorial;
		final int id = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findTutorialById(id);
		status = tutorial != null && !tutorial.isDraftMode();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Tutorial object = this.repository.findTutorialById(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		final Collection<Course> courses;
		final SelectChoices coursesChoices;

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());

		final Collection<TutorialSession> sessions;
		Double estimatedTime;
		sessions = this.repository.findTutorialSessionsByTutorialId(object.getId());
		estimatedTime = 0.;
		for (final TutorialSession ts : sessions)
			estimatedTime += ts.getHoursFromPeriod();

		Tuple tuple;
		tuple = super.unbind(object, "code", "tittle", "summary", "goals", "assistant.supervisor", "assistant.curriculum", "assistant.expertiseField", "assistant.link");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);
		tuple.put("estimatedTime", estimatedTime);

		super.getResponse().setData(tuple);
	}
}
