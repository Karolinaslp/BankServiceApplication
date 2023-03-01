package org.kaczucha.account.application;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.port.AccountServiceUseCase;
import org.kaczucha.account.application.port.TransferServiceUseCase;
import org.kaczucha.account.db.TransferJpaRepository;
import org.kaczucha.account.web.dto.TransferRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService implements TransferServiceUseCase {
    private final TransferJpaRepository repository;
    private final AccountServiceUseCase service;

    private final TransferMapper mapper;

    @Override
    public void createTransfer(TransferRequest request) {
        service.transfer(
                request.getFromAccountId(),
                request.getToAccountId(),
                request.getAmount(),
                request.getCurrency()
        );

        repository.save(mapper.toTransfer(request));
    }
}
