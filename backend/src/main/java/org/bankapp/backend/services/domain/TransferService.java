package org.bankapp.backend.services.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.TransferGetDTO;
import org.bankapp.backend.dtos.TransferRequestDTO;
import org.bankapp.backend.entities.domain.Account;
import org.bankapp.backend.entities.domain.Transfer;
import org.bankapp.backend.exceptions.IllegalTransferException;
import org.bankapp.backend.exceptions.IllegalTransferHistoryRequest;
import org.bankapp.backend.exceptions.NoSufficientFundsOnAccountException;
import org.bankapp.backend.exceptions.RecipientAccountNotFoundException;
import org.bankapp.backend.repostiories.domain.AccountRepository;
import org.bankapp.backend.repostiories.domain.TransferRepository;
import org.bankapp.backend.services.security.SessionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final SessionService sessionService;

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Transactional
    public void sendTransfer(String customerId, TransferRequestDTO transferRequestDTO) {
        Account customerFromAccount = accountRepository.findAccountByAccountNumberAndCustomerInfosCustomerId(
                transferRequestDTO.getFromAccount(),
                customerId
        ).orElseThrow(IllegalTransferException::new);
        Account recipientAccount = accountRepository.findById(transferRequestDTO.getRecipientAccountNumber())
                .orElseThrow(RecipientAccountNotFoundException::new);

        if (customerFromAccount.getBalance().compareTo(transferRequestDTO.getAmount()) < 0) {
            throw new NoSufficientFundsOnAccountException();
        }
        customerFromAccount.setBalance(customerFromAccount.getBalance()
                .subtract(transferRequestDTO.getAmount()));
        recipientAccount.setBalance(recipientAccount.getBalance()
                .add(transferRequestDTO.getAmount()));

        Transfer transfer = Transfer.builder()
                .title(transferRequestDTO.getTitle())
                .sender(customerFromAccount)
                .receiver(recipientAccount)
                .amount(transferRequestDTO.getAmount())
                .madeAt(Instant.now())
                .build();

        transferRepository.save(transfer);
    }

    public List<TransferGetDTO> getHistoryOfTransfers(String customerId, String accountNumber) {
        if (!accountRepository.existsAccountByAccountNumberAndCustomerInfosCustomerId(
                accountNumber,
                customerId)) {
            throw new IllegalTransferHistoryRequest();
        }

        List<Transfer> transfers = transferRepository.findTransfersBySenderAccountNumberOrReceiverAccountNumberOrderByMadeAt(
                accountNumber,
                accountNumber
        );
        return transfers.stream()
                .map(transfer -> transfer.getSender().getAccountNumber().equals(accountNumber) ?
                        TransferGetDTO.fromEntity(transfer, true) :
                        TransferGetDTO.fromEntity(transfer, false))
                .toList();
    }
}
