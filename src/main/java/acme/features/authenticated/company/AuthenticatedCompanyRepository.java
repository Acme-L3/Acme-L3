
package acme.features.authenticated.company;

import org.springframework.data.jpa.repository.Query;

import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

public interface AuthenticatedCompanyRepository extends AbstractRepository {

	@Query("SELECT u FROM UserAccount u WHERE u.id = :userAccountId")
	UserAccount findOneUserAccountById(int userAccountId);

	@Query("SELECT c FROM Company c WHERE c.userAccount.id = :userAccountId")
	Company findOneCompanyByUserAccountId(int userAccountId);

}
