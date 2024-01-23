package org.bankapp.backend.entities.security;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSecret {

    @EmbeddedId
    private CustomerSecretId id;

    private BigInteger secret;

    @Embeddable
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerSecretId implements Serializable {
        private String customerId;
        private Integer secretIndex;
    }
}
