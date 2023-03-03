package org.kaczucha.account.application.mapper;

import org.kaczucha.account.domain.Transfer;
import org.kaczucha.account.web.dto.TransferRequest;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TransferMapper {

    public Transfer toTransfer(TransferRequest transferRequest) {
        return Transfer.builder()
                .amount(transferRequest.getAmount())
                .currency(transferRequest.getCurrency())
                .fromAccountId(transferRequest.getToAccountId())
                .toAccountId(transferRequest.getToAccountId())
                .transactionDate(OffsetDateTime.now())
                .build();
    }
}
