package org.kaczucha.account.db;

import org.kaczucha.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJpaRepository extends JpaRepository<Account, Long> {

}
