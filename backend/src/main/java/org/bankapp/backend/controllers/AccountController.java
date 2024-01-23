package org.bankapp.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.AccountGetDTO;
import org.bankapp.backend.services.domain.AccountService;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("private/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountGetDTO> getAccounts(@CookieValue(name = SessionService.SESSION_COOKIE_NAME) String sessionId) {
        return accountService.getAccounts(sessionId);
    }
}
