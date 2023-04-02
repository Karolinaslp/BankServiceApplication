package org.karolina.account.web.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Value
@Builder
public class TransferResponse {
    boolean success;
    Long transferId;
    List<String> errors;

    public static TransferResponse success(Long transferId) {
        return new TransferResponse(true, transferId, Collections.emptyList());
    }

    public static TransferResponse failure(String... errors) {
        return new TransferResponse(false, null, Arrays.asList(errors));
    }

    public Long getTransferId() {
        return transferId;
    }
}
