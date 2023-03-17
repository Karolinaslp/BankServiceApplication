package org.karolina.account.application.port;

import org.karolina.account.web.dto.TransferRequest;

public interface TransferServiceUseCase {
    void createTransfer(TransferRequest request);
}
