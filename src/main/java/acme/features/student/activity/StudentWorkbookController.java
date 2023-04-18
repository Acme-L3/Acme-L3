
package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.activities.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class StudentWorkbookController extends AbstractController<Student, Activity> {

	@Autowired
	protected StudentWorkbookListService	listService;

	@Autowired
	protected StudentWorkbookShowService	showService;

	@Autowired
	protected StudentWorkbookCreateService	createService;

	@Autowired
	protected StudentActivityUpdateService	updateService;

	@Autowired
	protected StudentActivityDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
