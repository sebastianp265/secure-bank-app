package org.bankapp.backend.entities.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class Transfer {
    @Id
    @GeneratedValue
    private Long transferId;

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private String title;

    private BigDecimal amount;

    private Instant sentAt;
}
