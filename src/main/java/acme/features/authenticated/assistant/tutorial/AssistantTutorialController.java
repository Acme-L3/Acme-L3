
package acme.features.authenticated.assistant.tutorial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.tutorial.Tutorial;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantTutorialController extends AbstractController<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialCreateService	createService;

	@Autowired
	protected AssistantTutorialDeleteService	deleteService;

	@Autowired
	protected AssistantTutorialListMineService	listMineService;

	@Autowired
	protected AssistantTutorialPublishService	publishService;

	@Autowired
	protected AssistantTutorialShowService		showService;

	@Autowired
	protected AssistantTutorialUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
	}
}
