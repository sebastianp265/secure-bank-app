package org.bankapp.backend.repostiories.domain;

import org.bankapp.backend.dtos.AccountGetDTO;
import org.bankapp.backend.entities.domain.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;


public interface AccountRepository extends CrudRepository<Account, String> {

    Optional<Account> findAccountByAccountNumberAndCustomerInfosCustomerId(String accountNumber, String customerId);

    Set<Account> findAccountsByCustomerInfosCustomerId(String customerId);

    boolean existsAccountByAccountNumberAndCustomerInfosCustomerId(String accountNumber, String customerId);
}
