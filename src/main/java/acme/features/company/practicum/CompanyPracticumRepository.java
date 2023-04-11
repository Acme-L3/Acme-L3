
package acme.features.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :companyId")
	Collection<Practicum> findPracticaByCompanyId(int companyId);

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	Practicum findPracticumById(int id);

}
