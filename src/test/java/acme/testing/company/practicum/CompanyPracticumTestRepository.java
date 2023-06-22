
package acme.testing.company.practicum;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumTestRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.company.userAccount.username = :username")
	List<Practicum> findPracticumsByCompany(String username);

	@Query("SELECT p FROM Practicum p WHERE p.company.userAccount.username = :username AND p.draftMode = true")
	List<Practicum> findPublishedPracticumsByCompany(String username);

	@Query("SELECT p FROM Practicum p WHERE p.company.userAccount.username = :username AND p.draftMode = false")
	List<Practicum> findNotPublishedPracticumsByCompany(String username);
}
