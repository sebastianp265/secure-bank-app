package org.bankapp.backend.entities.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue
    private UUID id;

    private String cardNumber;

    private String cvv;

    private String validThru;
}
