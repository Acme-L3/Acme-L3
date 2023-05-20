
package acme.features.authenticated.tutorialSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.session.TutorialSession;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedTutorialSessionController extends AbstractController<Authenticated, TutorialSession> {

	@Autowired
	protected AuthenticatedTutorialSessionListService	listService;

	@Autowired
	protected AuthenticatedTutorialSessionShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
