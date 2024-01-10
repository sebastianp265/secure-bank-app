package org.bankapp.backend.entities.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
public class Account {

    @Id
    private String accountNumber;

    private BigDecimal balance;

    @OneToMany
    @JoinColumn(name = "account_number")
    private Set<Card> cards;

}
