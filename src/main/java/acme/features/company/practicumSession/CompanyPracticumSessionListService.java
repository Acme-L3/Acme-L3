
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.practicums.Practicum;
import acme.entitites.session.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repo;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		int masterId;
		Practicum practicum;
		boolean status;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(masterId);
		status = practicum != null && super.getRequest().getPrincipal().hasRole(practicum.getCompany());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int practicumId;
		Collection<PracticumSession> practicumSessions;
		Practicum practicum;
		boolean addendumCheck;

		practicumId = super.getRequest().getData("masterId", int.class);
		practicum = this.repo.findPracticumById(practicumId);
		addendumCheck = this.repo.findAddendumByPracticumId(true, practicumId) != null;
		practicumSessions = this.repo.findPracticumSessionsByPracticumId(practicumId);

		super.getBuffer().setData(practicumSessions);
		super.getResponse().setGlobal("masterId", practicumId);
		super.getResponse().setGlobal("draftMode", practicum.getDraftMode());
		super.getResponse().setGlobal("addendumCheck", addendumCheck);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;
		Tuple tuple;
		int practicumId;

		practicumId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(object, "title", "summary");
		tuple.put("masterId", practicumId);

		super.getResponse().setData(tuple);
	}
}
