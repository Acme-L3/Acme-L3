
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entitites.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedNoteRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Date deadline;
		// Las notas a mostrar deben ser no más antiguas a 1 mes. CSV no pilla esas fechas.
		deadline = MomentHelper.deltaFromCurrentMoment(-365, ChronoUnit.DAYS);
		final Collection<Note> objects = this.repository.findRecentNotes(deadline);
		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;
		final Tuple tuple = super.unbind(object, "moment", "title", "author", "message", "email", "link");
		super.getResponse().setData(tuple);

	}

}
