package org.karolina.client.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientResponse {

    private long id;

    private String name;

    private String email;
    private List<Long> accounts;

}
