package org.karolina.client.db;

import org.karolina.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmailIgnoreCase(String email);

    List<Client> findByNameIgnoreCase(String name);

    @Query("SELECT DISTINCT c FROM Client c JOIN FETCH c.accounts")
    List<Client> findAllEager();

    Page<Client> findByNameIgnoreCase(String name, Pageable pageable);

    void deleteById(Long id);
}
