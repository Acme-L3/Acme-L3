
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.lecture.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select l from Lecture l")
	Collection<Lecture> findAllLectures();

	@Query("select l from CourseLecture cl join cl.lecture l where cl.course.id = :id")
	Collection<Lecture> findAllLecturesByCourse(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findAllLecturesByLecturerId(int id);

	@Query("select l from Lecturer l where l.userAccount.id = :id")
	Lecturer findOneLecturerByUserAccountId(int id);

	@Query("select count(c) from CourseLecture c where c.lecture.id = :id")
	Integer countCoursesByLectureId(int id);

	@Query("select l from CourseLecture c join c.lecture l where c.course.id = :courseId and l.lecturer.id = :lecturerId")
	Collection<Lecture> findLecturesByCourseAndLecturer(int courseId, int lecturerId);

}
