package org.bankapp.backend.controllers.domain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.AccountGetDTO;
import org.bankapp.backend.services.domain.AccountService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.bankapp.backend.interceptors.AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE;

@RestController
@RequestMapping("private/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final SessionService sessionService;

    @GetMapping
    public List<AccountGetDTO> getAccounts(@RequestAttribute(name = CUSTOMER_ID_ATTRIBUTE) String customerId,
                                           HttpServletResponse response) {
        List<AccountGetDTO> accountGetDTOS = accountService.getAccounts(customerId);
        response.addHeader("Set-Cookie",
                sessionService.generateCookie(
                        sessionService.createSession(customerId)
                )
        );
        return accountGetDTOS;
    }
}
