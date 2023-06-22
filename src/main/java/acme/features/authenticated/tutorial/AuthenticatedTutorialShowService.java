
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

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
		final Collection<Assistant> assistants;
		final SelectChoices coursesChoices;
		final SelectChoices assistantsChoices;

		courses = this.repository.findAllCourses();
		coursesChoices = SelectChoices.from(courses, "title", object.getCourse());

		assistants = this.repository.findAllAssistants();
		assistantsChoices = SelectChoices.from(assistants, "supervisor", object.getAssistant());

		Tuple tuple;
		tuple = super.unbind(object, "code", "tittle", "summary", "goals", "startDate", "endDate");
		tuple.put("course", coursesChoices.getSelected().getKey());
		tuple.put("courses", coursesChoices);
		tuple.put("assistant", object.getAssistant().getIdentity().getFullName());
		tuple.put("assistants", assistantsChoices);

		super.getResponse().setData(tuple);
	}
}
