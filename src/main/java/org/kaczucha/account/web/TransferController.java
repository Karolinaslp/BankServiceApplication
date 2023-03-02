package org.kaczucha.account.web;

import lombok.RequiredArgsConstructor;
import org.kaczucha.account.application.port.TransferServiceUseCase;
import org.kaczucha.account.web.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferServiceUseCase service;

    @PostMapping(path = "/api/transfer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTransfer(@RequestBody TransferRequest request) {
        service.createTransfer(request);
    }
}
