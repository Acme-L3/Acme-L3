
package acme.features.lecturer.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.course.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerCourseController extends AbstractController<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseCreateService			createService;

	@Autowired
	protected LecturerCourseUpdateService			updateService;

	@Autowired
	protected LecturerCourseDeleteService			deleteService;

	@Autowired
	protected LecturerCourseListService				listService;

	@Autowired
	protected LecturerCourseShowService				showService;

	@Autowired
	protected LecturerCoursePublishService			publishService;

	@Autowired
	protected LecturerCourseAddLectureService		addLectureService;

	@Autowired
	protected LecturerCourseRemoveLectureService	removeLectureService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addCustomCommand("add-lecture", "update", this.addLectureService);
		super.addCustomCommand("remove-lecture", "update", this.removeLectureService);
	}

}
