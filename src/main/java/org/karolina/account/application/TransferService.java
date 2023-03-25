package org.karolina.account.application;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.mapper.TransferMapper;
import org.karolina.account.application.port.AccountServiceUseCase;
import org.karolina.account.application.port.TransferServiceUseCase;
import org.karolina.account.db.TransferJpaRepository;
import org.karolina.account.domain.Transfer;
import org.karolina.account.web.dto.TransferRequest;
import org.karolina.account.web.dto.TransferResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService implements TransferServiceUseCase {
    private final TransferJpaRepository repository;
    private final AccountServiceUseCase service;
    private final TransferMapper mapper;

    @Override
    public TransferResponse createTransfer(TransferRequest request) {
        service.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount(),
                request.getCurrency()
        );

        Transfer transfer = repository.save(mapper.toTransfer(request));
        return TransferResponse.success(transfer.getId());
    }
}
