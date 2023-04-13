
package acme.features.authenticated.student;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedStudentRepository extends AbstractRepository {

	@Query("select uA from UserAccount uA where uA.id = :userAccountId")
	UserAccount findUserAccountById(int userAccountId);

}
