
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select l from Lecture l")
	Collection<Lecture> findAllLectures();

	@Query("select l from Lecture l where l.lecturer.userAccount.id = :id")
	Collection<Lecture> findAllLecturesByLectureId(int id);

}
