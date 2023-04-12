
package acme.features.company.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.practicums.Practicum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticumController extends AbstractController<Company, Practicum> {

	@Autowired
	protected CompanyPracticumListService	listService;

	@Autowired
	protected CompanyPracticumShowService	showService;

	@Autowired
	protected CompanyPracticumCreateService	createService;

	@Autowired
	protected CompanyPracticumDeleteService	deleteService;

	@Autowired
	protected CompanyPracticumUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
