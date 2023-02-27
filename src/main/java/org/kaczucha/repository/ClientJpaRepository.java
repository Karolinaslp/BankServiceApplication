package org.kaczucha.repository;

import org.kaczucha.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {
    //@Query("SELECT c FROM Client c join Account a ON c.id=a.userId  WHERE c.email= :email")
    Client findByEmail(@Param("email") String email);

    List<Client> findByNameIgnoreCase(String name);

    Page<Client> findByNameIgnoreCase(String name, Pageable pageable);
    void deleteByEmail(String email);
}
