package org.karolina.account.application.port;

import org.karolina.account.web.dto.TransferRequest;
import org.karolina.account.web.dto.TransferResponse;

public interface TransferServiceUseCase {
    TransferResponse createTransfer(TransferRequest request);

}
