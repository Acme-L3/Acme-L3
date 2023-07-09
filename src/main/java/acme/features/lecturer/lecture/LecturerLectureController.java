
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.lecture.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureCreateService	createService;

	@Autowired
	protected LecturerLectureUpdateService	updateService;

	@Autowired
	protected LecturerLectureDeleteService	deleteService;

	@Autowired
	protected LecturerLectureListService	listService;

	@Autowired
	protected LecturerLectureShowService	showService;

	@Autowired
	protected LecturerLecturePublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
