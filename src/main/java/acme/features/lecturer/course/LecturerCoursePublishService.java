
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.course.CourseType;
import acme.features.administrator.systemconfiguration.AdministratorSystemConfigurationRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository						repository;

	@Autowired
	protected AdministratorSystemConfigurationRepository	systemConfigurationRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		final boolean status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findCourseById(id);
		final boolean logged = object.getLecturer().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();
		super.getResponse().setAuthorised(status && logged && !object.isPublished());
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findCourseById(id);
		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		//		if (!super.getBuffer().getErrors().hasErrors("moment")) {
		//			Date moment;
		//
		//			moment = MomentHelper.getCurrentMoment();
		//			super.state(!MomentHelper.isAfter(object.getMoment(), moment), "moment", "administrator.offer.form.error.moment-must-be-past");
		//		}
		//		if (!super.getBuffer().getErrors().hasErrors("heading"))
		//			super.state(object.getHeading().length() <= 75, "heading", "administrator.offer.form.error.heading-lenght");
		//		if (!super.getBuffer().getErrors().hasErrors("summary"))
		//			super.state(object.getSummary().length() <= 100, "summary", "administrator.offer.form.error.summary-lenght");
		//		if (!super.getBuffer().getErrors().hasErrors("startAvailability")) {
		//			Date startDate;
		//
		//			startDate = MomentHelper.deltaFromCurrentMoment(1, ChronoUnit.HOURS);
		//			super.state(MomentHelper.isAfter(object.getStartAvailability(), startDate), "startAvailability", "administrator.offer.form.error.start-too-early");
		//		}
		//		if (!super.getBuffer().getErrors().hasErrors("endAvailability")) {
		//			Date endDate;
		//			final Calendar c = Calendar.getInstance();
		//			c.setTime(object.getStartAvailability());
		//
		//			c.add(Calendar.DATE, 7);
		//			endDate = c.getTime();
		//			super.state(MomentHelper.isAfter(object.getEndAvailability(), endDate), "endAvailability", "administrator.offer.form.error.end-too-early");
		//		}
		//		if (!super.getBuffer().getErrors().hasErrors("price")) {
		//			super.state(object.getPrice().getAmount() > 0, "price", "administrator.offer.form.error.negative-price");
		//
		//			final SystemConfiguration systemConfiguration = this.systemConfigurationRepository.findSystemConfiguration().get(0);
		//			final boolean b = Arrays.asList(systemConfiguration.getAcceptedCurrencies().split(",")).contains(object.getPrice().getCurrency());
		//			super.state(b, "price", "administrator.offer.form.error.not-found-currency");
		//	}

	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		object.setPublished(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		final SelectChoices choices = SelectChoices.from(CourseType.class, object.getCourseType());
		final Tuple tuple = super.unbind(object, "code", "title", "retailPrice", "abstractText", "courseType", "link");
		tuple.put("types", choices);
		tuple.put("published", object.isPublished());
		super.getResponse().setData(tuple);
	}

}
