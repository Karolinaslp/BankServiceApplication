package org.kaczucha.account.db;

import org.kaczucha.account.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferJpaRepository extends JpaRepository<Transfer, Long> {

}
