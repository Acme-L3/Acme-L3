
package acme.features.authenticated.handsOnSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.session.HandsOnSession;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedHandsOnSessionController extends AbstractController<Authenticated, HandsOnSession> {

	@Autowired
	protected AuthenticatedHandsOnSessionListService	listService;

	@Autowired
	protected AuthenticatedHandsOnSessionShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
