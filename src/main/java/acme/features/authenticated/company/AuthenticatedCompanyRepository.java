
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("SELECT u FROM UserAccount u WHERE u.id = :userAccountId")
	UserAccount findOneUserAccountById(int userAccountId);

}
