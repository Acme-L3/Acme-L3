
package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.activities.Activity;
import acme.entitites.course.Course;
import acme.entitites.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentEnrolmentRepository extends AbstractRepository {

	@Query("select e from Enrolment e")
	Collection<Enrolment> findAllEnrolments();

	@Query("select e from Enrolment e where e.student.id = :id")
	Collection<Enrolment> findEnrolmentByStudentId(int id);

	@Query("select e.code from Enrolment e where e.student.id = :id")
	Collection<String> findEnrolmentCodesByStudentId(int id);

	@Query("select e.course.code from Enrolment e where e.student.id = :id")
	Collection<String> findCourseCodeEnrolmentByStudentId(int id);

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select c from Course c")
	Collection<Course> findAllCourses();

	@Query("select c from Course c where c.isPublished = true")
	Collection<Course> findAllPublishedCourses();

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select s from Student s where s.id = :id")
	Student findStudentById(int id);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);

	@Query("select a from Activity a where a.id = :id")
	Activity findActivityById(int id);

	@Query("select a.enrolment from Activity a where a.id = :activityId")
	Enrolment findEnrolmentByActivityId(int activityId);

	@Query("select e from Enrolment e where e.code = :code")
	Enrolment findEnrolmentByCode(String code);

	@Query("select e.code from Enrolment e")
	Collection<String> findAllCodesFromEnrolments();

	@Query("select c from Course c where c.isPublished = false")
	Collection<Course> findNotIsPublishedCourses();

	@Query("select e.code from Enrolment e where e.code =:code")
	String findCode(String code);

}
