package org.bankapp.backend.entities.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue
    private Long transferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_account_number")
    private Account sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_account_number")
    private Account receiver;

    private String title;

    private BigDecimal amount;

    private Instant madeAt;
}
