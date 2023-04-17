
package acme.features.company.practicumSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.practicums.Practicum;
import acme.entitites.session.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("SELECT p FROM PracticumSession p WHERE p.id = :practicumSessionId")
	PracticumSession findPracticumSessionById(int practicumSessionId);

	@Query("SELECT p FROM PracticumSession p WHERE p.practicum.id = :practicumId")
	Collection<PracticumSession> findPracticumSessionsByPracticumId(int practicumId);

	@Query("SELECT p FROM Practicum p WHERE p.id = :masterId")
	Practicum findPracticumById(int masterId);

	@Query("SELECT p.practicum FROM PracticumSession p WHERE p.id = :id")
	Practicum findPracticumByPracticumSessionId(int id);

}
