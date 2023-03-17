package org.karolina.client.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRequest {
    @NotBlank(message = "Please provide client name.")
    private String name;

    @NotBlank(message = "Please provide client email.")
    @UniqueElements
    private String email;

}
