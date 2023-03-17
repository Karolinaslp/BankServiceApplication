package org.karolina.account.application.mapper;

import org.karolina.account.domain.Transfer;
import org.karolina.account.web.dto.TransferRequest;
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
