package org.bankapp.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.AccountGetDTO;
import org.bankapp.backend.interceptors.AuthorizationInterceptor;
import org.bankapp.backend.services.domain.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("private/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountGetDTO> getAccounts(@RequestAttribute(name = AuthorizationInterceptor.CUSTOMER_ID_ATTRIBUTE) String customerId) {
        return accountService.getAccounts(customerId);
    }
}
