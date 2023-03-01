package org.kaczucha.account.application.port;

import org.kaczucha.account.web.dto.TransferRequest;

public interface TransferServiceUseCase {
    void createTransfer(TransferRequest request);
}
