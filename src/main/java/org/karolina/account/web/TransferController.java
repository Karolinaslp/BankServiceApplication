package org.karolina.account.web;

import lombok.RequiredArgsConstructor;
import org.karolina.account.application.port.TransferServiceUseCase;
import org.karolina.account.web.dto.TransferRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/transfer")
public class TransferController {
    private final TransferServiceUseCase service;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createTransfer(@RequestBody TransferRequest request) {
        service.createTransfer(request);
    }
}
