package org.bankapp.backend.repostiories.domain;

import org.bankapp.backend.entities.domain.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account, String> {
}
