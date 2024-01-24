package org.bankapp.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.TransferGetDTO;
import org.bankapp.backend.dtos.TransferRequestDTO;
import org.bankapp.backend.interceptors.AuthorizationInterceptor;
import org.bankapp.backend.services.domain.TransferService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("private/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping()
    public void sendTransfer(@RequestAttribute(name = AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE) String customerId,
                             @Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        transferService.sendTransfer(customerId, transferRequestDTO);
    }

    @GetMapping("/history/{accountNumber}")
    public List<TransferGetDTO> getHistoryOfTransfers(@RequestAttribute(name = AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE) String customerId,
                                                      @PathVariable String accountNumber) {
        return transferService.getHistoryOfTransfers(customerId, accountNumber);
    }
}
