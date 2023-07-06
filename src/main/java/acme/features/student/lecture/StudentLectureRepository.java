
package acme.features.student.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.course.id = :courseId")
	Collection<Lecture> findLectureByCourseId(Integer courseId);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

}
