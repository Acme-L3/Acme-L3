
package acme.features.student.enrolment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.course.Course;
import acme.entitites.enrolments.Enrolment;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentEnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	@Autowired
	protected StudentEnrolmentRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;
		final Principal principal;
		Student student;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repo.findEnrolmentById(id);
		principal = super.getRequest().getPrincipal();
		student = this.repo.findStudentById(principal.getActiveRoleId());
		status = student != null && enrolment.getStudent().equals(student) && enrolment.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Enrolment object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repo.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("holderName")) {
			String holderName;
			holderName = object.getHolderName();

			super.state(holderName.length() != 0, "holderName", "student.enrolment.error.holderName.null");
		}

		if (!super.getBuffer().getErrors().hasErrors("lowerNibble")) {
			String lowerNibble;
			lowerNibble = object.getLowerNibble();
			super.state(lowerNibble.length() != 0, "lowerNibble", "student.enrolment.error.lowerNibble.null");
			super.state(lowerNibble.length() >= 13, "lowerNibble", "student.enrolment.error.lowerNibble.notValidNumber");
			super.state(lowerNibble.length() <= 18, "lowerNibble", "student.enrolment.error.lowerNibble.notValidNumber");
		}

		final String cvc = super.getRequest().getData("cvc", String.class);
		if (!super.getBuffer().getErrors().hasErrors("cvc"))
			super.state(cvc.length() != 0 && cvc.matches("^\\d{3}$"), "cvc", "student.enrolment.error.cvc.matches");

		final String expiryDate = super.getRequest().getData("expiryDate", String.class);
		final DateFormat format = new SimpleDateFormat("MM/yy");
		try {
			final Date dateParse = format.parse(expiryDate);
			final int mes = Integer.parseInt(expiryDate.split("/")[0]);
			if (mes < 1 || mes > 12)
				super.state(false, "expiryDate", "student.enrolment.error.expiryDate.matches");
			if (MomentHelper.isBefore(dateParse, MomentHelper.getCurrentMoment()))
				super.state(false, "expiryDate", "student.enrolment.error.expiryDate.isBefore");
		} catch (final ParseException e) {
			super.state(false, "expiryDate", "student.enrolment.error.expiryDate.matches");
		}
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;

		object.setDraftMode(false);

		final String lowerNibble = super.getRequest().getData("lowerNibble", String.class);
		final String lastNumber = lowerNibble.substring(lowerNibble.length() - 4);
		object.setLowerNibble(lastNumber);

		final String holderName = super.getRequest().getData("holderName", String.class);
		object.setHolderName(holderName);

		this.repo.save(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		Integer enrolmentId;
		Enrolment enrolment;

		enrolmentId = super.getRequest().getData("id", int.class);
		enrolment = this.repo.findEnrolmentById(enrolmentId);

		super.bind(object, "code", "motivation", "goal", "holderName", "lowerNibble");

	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		final String cvc = "";
		final String expiryDate = "";

		final Course course = object.getCourse();

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "lowerNibble", "holderName", "draftMode");
		tuple.put("courseShow", course.getCode());
		tuple.put("cvc", cvc);
		tuple.put("expiryDate", expiryDate);

		super.getResponse().setData(tuple);
	}

}
