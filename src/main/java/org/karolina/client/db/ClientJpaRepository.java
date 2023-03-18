package org.karolina.client.db;

import org.karolina.client.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientJpaRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmailToLowerCase(String email);

    List<Client> findByNameIgnoreCase(String name);

    List<Client> getAllClients();

    Page<Client> findByNameIgnoreCase(String name, Pageable pageable);

    void deleteById(Long id);
}
