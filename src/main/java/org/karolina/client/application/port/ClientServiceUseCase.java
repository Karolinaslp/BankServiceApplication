package org.karolina.client.application.port;

import lombok.*;
import org.karolina.client.domain.Client;
import org.karolina.client.web.dto.ClientRequest;
import org.karolina.client.web.dto.ClientResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientServiceUseCase {
    void save(ClientRequest clientRequest);

    Optional<Client> findByEmail(String email);

    void deleteById(Long id);

    List<Client> findAll();

    ClientResponse findResponseByEmail(String email);

    UpdateClientResponse updateClient(UpdateClientCommand command);

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateClientCommand {
        Long id;
        String name;
        String email;
        Set<Long> accounts;
    }

    @Value
    class UpdateClientResponse {
        public static UpdateClientResponse SUCCESS = new UpdateClientResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;

    }
}
