
package acme.features.authenticated.assistant.handsOnSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.session.HandsOnSession;
import acme.framework.controllers.AbstractController;
import acme.roles.Assistant;

@Controller
public class AssistantHandsOnSessionController extends AbstractController<Assistant, HandsOnSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantHandsOnSessionCreateService	createService;

	@Autowired
	protected AssistantHandsOnSessionDeleteService	deleteService;

	@Autowired
	protected AssistantHandsOnSessionListService	listService;

	@Autowired
	protected AssistantHandsOnSessionShowService	showService;

	@Autowired
	protected AssistantHandsOnSessionUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
	}
}
