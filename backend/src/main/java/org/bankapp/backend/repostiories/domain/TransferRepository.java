package org.bankapp.backend.repostiories.domain;

import org.bankapp.backend.entities.domain.Transfer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<Transfer, Long> {

    List<Transfer> findTransfersBySenderAccountNumberOrReceiverAccountNumberOrderByMadeAt(
            String senderAccountNumber,
            String receiverAccountNumber);

}
