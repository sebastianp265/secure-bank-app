package org.bankapp.backend.repostiories.security;

import org.bankapp.backend.entities.security.CustomerSecret;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSecretRepository extends
        CrudRepository<CustomerSecret, CustomerSecret.CustomerSecretId> {

    void deleteAllByIdCustomerId(String customerId);

    int countByIdCustomerId(String customerId);

}
