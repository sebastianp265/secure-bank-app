package org.bankapp.backend.repostiories;

import org.bankapp.backend.entities.security.CustomerSecret;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSecretRepository extends
        CrudRepository<CustomerSecret, CustomerSecret.CustomerSecretId> {

    void deleteAllByIdCustomerId(String customerId);

    boolean existsByIdCustomerId(String customerId);

}
