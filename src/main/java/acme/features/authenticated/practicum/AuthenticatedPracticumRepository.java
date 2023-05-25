
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPracticumRepository extends AbstractRepository {

	@Query("SELECT p FROM Practicum p WHERE p.course.id = :courseId AND p.draftMode = FALSE")
	Collection<Practicum> findAllPracticumsByCourseIdPublished(int courseId); // Only published Practicums

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	Practicum findPracticumById(int id);

}
