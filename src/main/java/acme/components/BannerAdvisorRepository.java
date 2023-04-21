
package acme.components;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.banner.Banner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BannerAdvisorRepository extends AbstractRepository {

	@Query("select b from Banner b where :present between b.startDate and b.endDate OR b.startDate = :present OR b.endDate = :present")
	Collection<Banner> findBannerFromPresent(Date present);

}
