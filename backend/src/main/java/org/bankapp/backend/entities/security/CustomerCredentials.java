package org.bankapp.backend.entities.security;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
public class CustomerCredentials {

    @Id
    private String customerId;

    private String keyHash;

    public static final int REQUIRED_PASSWORD_PARTS = 5;

    public static final int MIN_PASSWORD_LENGTH = 10;
    public static final int MAX_PASSWORD_LENGTH = 32;

    @OneToMany(mappedBy = "id.customerId", cascade = CascadeType.MERGE)
    private Set<CustomerSecret> secrets;

    public static final int EXPIRATION_SECONDS = 15 * 60;

    private String changePasswordToken;

    private Instant changePasswordTokenExpiration;

}
