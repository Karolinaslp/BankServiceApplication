package org.karolina.account.db;

import org.karolina.account.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferJpaRepository extends JpaRepository<Transfer, Long> {

}
