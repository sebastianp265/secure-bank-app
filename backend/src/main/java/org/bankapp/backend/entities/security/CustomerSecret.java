package org.bankapp.backend.entities.security;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class CustomerSecret {

    @Embeddable
    @Data
    public static class CustomerSecretId implements Serializable {
        private String customerId;
        private Integer secretIndex;
    }

    @EmbeddedId
    private CustomerSecretId id;

    private Long secret;
}
