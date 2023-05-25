
package acme.testing.company.practicumSession;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.session.PracticumSession;
import acme.framework.repositories.AbstractRepository;

public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.company.userAccount.username = :username")
	List<PracticumSession> findPracticumSessionsByCompany(String username);
}
