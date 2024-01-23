package org.bankapp.backend.repostiories.domain;

import org.bankapp.backend.entities.domain.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {
}
