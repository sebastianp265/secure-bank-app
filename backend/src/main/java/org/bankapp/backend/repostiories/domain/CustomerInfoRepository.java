package org.bankapp.backend.repostiories.domain;

import org.bankapp.backend.entities.domain.CustomerInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends CrudRepository<CustomerInfo, String> {
}
