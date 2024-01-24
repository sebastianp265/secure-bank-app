package org.bankapp.backend.services.domain;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.AccountGetDTO;
import org.bankapp.backend.entities.domain.Account;
import org.bankapp.backend.repostiories.domain.AccountRepository;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final SessionService sessionService;
    private final AccountRepository accountRepository;

    public List<AccountGetDTO> getAccounts(String customerId) {
        Set<Account> accounts = accountRepository.findAccountsByCustomerInfosCustomerId(customerId);

        return accounts.stream()
                .map(AccountGetDTO::fromEntity)
                .toList();
    }
}
