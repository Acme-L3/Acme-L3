
package acme.features.administrator.systemconfiguration;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.systemconfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	//	@Query("select b from Bulletin b where b.id = :id")
	//	Bulletin findBulletinById(int id);
	//
	//	@Query("select b from Bulletin b")
	//	Collection<Bulletin> findAllBulletins();

	@Query("select sc from SystemConfiguration sc")
	List<SystemConfiguration> findSystemConfiguration();
}
