package org.bankapp.backend.entities.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Card {

    @Id
    private String cardNumber;

    private String cvv;

    private String validThru;
}
