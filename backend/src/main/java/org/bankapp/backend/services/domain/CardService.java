package org.bankapp.backend.services.domain;

import lombok.RequiredArgsConstructor;
import org.bankapp.backend.dtos.CardPreviewGetDTO;
import org.bankapp.backend.entities.domain.Account;
import org.bankapp.backend.entities.domain.Card;
import org.bankapp.backend.exceptions.IllegalOperationException;
import org.bankapp.backend.repostiories.domain.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CardService {

    private final AccountRepository accountRepository;

    public List<CardPreviewGetDTO> getCardPreviews(String customerId, String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumberAndCustomerInfosCustomerId(accountNumber,
                        customerId)
                .orElseThrow(IllegalOperationException::new);
        Set<Card> cards = account.getCards();
        return cards.stream()
                .map(card -> CardPreviewGetDTO.builder()
                        .cardNumberBeginning(card.getCardNumber().substring(0, 4))
                        .id(card.getId().toString())
                        .build())
                .toList();
    }

    public String getCardNumber(String customerId, String accountNumber, String cardId) {
        return getCard(customerId, accountNumber, cardId).getCardNumber();
    }

    public String getCvv(String customerId, String accountNumber, String cardId) {
        return getCard(customerId, accountNumber, cardId).getCvv();
    }

    public String getValidThru(String customerId, String accountNumber, String cardId) {
        return getCard(customerId, accountNumber, cardId).getValidThru();
    }

    private Card getCard(String customerId, String accountNumber, String cardId) {
        Account account = accountRepository.findAccountByAccountNumberAndCustomerInfosCustomerId(accountNumber, customerId)
                .orElseThrow(IllegalOperationException::new);
        return account.getCards().stream()
                .filter(c -> c.getId().toString().equals(cardId))
                .findFirst()
                .orElseThrow(IllegalOperationException::new);
    }
}
