
package acme.features.authenticated.offer;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.offers.Offer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedOfferRepository extends AbstractRepository {

	@Query("select b from Offer b where b.id = :id")
	Offer findOneOfferById(int id);

	@Query("select b from Offer b where b.moment >= :deadline")
	Collection<Offer> findRecentOffers(Date deadline);

}
