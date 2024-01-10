package org.bankapp.backend.entities.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class CustomerInfo {

    @Id
    private String customerId;

    private String firstName;

    private String secondName;

    private String surname;

    private String email;

    private String pesel;

    private String identityCardNumber;

    @ManyToMany
    @JoinTable(
            name = "customer_account",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "account_number")
    )
    private Set<Account> accounts;
}
