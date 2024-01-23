package org.bankapp.backend.repostiories.security;

import org.bankapp.backend.entities.security.CustomerSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerSessionRepository extends CrudRepository<CustomerSession, UUID> {

    void deleteAllByCustomerId(String customerId);
}
