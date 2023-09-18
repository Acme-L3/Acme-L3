
package acme.features.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Student;

public interface StudentActivityRepository extends AbstractRepository {

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

	@Query("select s from Student s where s.id=:principalId")
	Student findStudentByPrincipalId(int principalId);

}
