
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

@Repository
public interface StudentWorkBookRepository extends AbstractRepository {

	@Query("select a from Activity a")
	Collection<Activity> findAllActivities();

	@Query("select e from Enrolment e where e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);

	@Query("select a from Activity a where a.id = :id")
	Activity findActivityById(int id);

	@Query("select s from Student s where s.id = :id")
	Student findStudentById(int id);

	@Query("select e from Enrolment e")
	Collection<Enrolment> findAllEnrolments();

}
