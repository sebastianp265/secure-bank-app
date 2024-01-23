package org.bankapp.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.TransferRequestDTO;
import org.bankapp.backend.services.domain.TransferService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("private/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping()
    public void sendTransfer(@CookieValue(name = SessionService.SESSION_COOKIE_NAME) String sessionId,
                             @Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        transferService.sendTransfer(sessionId, transferRequestDTO);
    }
}
