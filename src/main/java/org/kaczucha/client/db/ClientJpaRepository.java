package org.kaczucha.client.db;

import org.kaczucha.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {
    Client findByEmailIgnoreCase(String email);

    List<Client> findByNameIgnoreCase(String name);

    Page<Client> findByNameIgnoreCase(String name, Pageable pageable);

    void deleteById(Long id);
}
