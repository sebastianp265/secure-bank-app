package org.bankapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.entities.domain.Account;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class AccountGetDTO {
    String accountNumber;
    BigDecimal balance;

    public static AccountGetDTO fromEntity(Account account) {
        return AccountGetDTO.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build();
    }
}
