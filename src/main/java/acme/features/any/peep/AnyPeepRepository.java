
package acme.features.any.peep;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.peeps.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("select p from Peep p where p.id = :id")
	Peep findOnePeepById(int id);

	@Query("select p from Peep p where p.moment >= :deadline")
	Collection<Peep> findRecentPeeps(Date deadline);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
