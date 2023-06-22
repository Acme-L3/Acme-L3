
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entitites.activities.Activity;
import acme.entitites.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("select e from Enrolment e where e.student.userAccount.username = :username")
	Collection<Enrolment> findEnrolemntByStudentUsername(String username);

	@Query("select a from Activity a where a.enrolment.id = :id")
	Collection<Activity> findActivityByEnrolment(int id);

	@Query("select e from Enrolment e where e.draftMode = true and e.student.userAccount.username = :string")
	Collection<Enrolment> findEnrolmentWithDrafMode(String string);

	@Query("select e from Enrolment e where e.draftMode = false and e.student.userAccount.username = :string")
	Collection<Enrolment> findEnrolmentWithoutDraftMode(String string);

}
