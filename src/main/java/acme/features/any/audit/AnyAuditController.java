
package acme.features.any.audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entitites.audits.Audit;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyAuditController extends AbstractController<Any, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyAuditListService	listService;

	@Autowired
	protected AnyAuditShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
