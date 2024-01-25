package org.bankapp.backend.controllers.domain;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.TransferGetDTO;
import org.bankapp.backend.dtos.TransferRequestDTO;
import org.bankapp.backend.services.domain.TransferService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequestMapping("private/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final SessionService sessionService;

    @PostMapping()
    public void sendTransfer(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                             @Valid @RequestBody TransferRequestDTO transferRequestDTO,
                             HttpServletResponse response) {
        transferService.sendTransfer(customerId, transferRequestDTO);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
    }

    @GetMapping("/history/{accountNumber}")
    public List<TransferGetDTO> getHistoryOfTransfers(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                                      @PathVariable String accountNumber,
                                                      HttpServletResponse response) {
        List<TransferGetDTO> transferGetDTOS = transferService.getHistoryOfTransfers(customerId, accountNumber);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return transferGetDTOS;
    }
}
