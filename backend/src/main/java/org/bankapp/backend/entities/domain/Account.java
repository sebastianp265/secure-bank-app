package org.bankapp.backend.entities.domain;

import jakarta.persistence.*;
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

    @ManyToMany(mappedBy = "accounts")
    private Set<CustomerInfo> customerInfos;

}
