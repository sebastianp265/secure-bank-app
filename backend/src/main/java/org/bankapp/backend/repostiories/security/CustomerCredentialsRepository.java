package org.bankapp.backend.repostiories.security;

import org.bankapp.backend.entities.security.CustomerCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerCredentialsRepository extends CrudRepository<CustomerCredentials, String> {

}
