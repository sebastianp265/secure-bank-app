package org.bankapp.backend.entities.security;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class CustomerCredentials {

    public static final int REQUIRED_PASSWORD_PARTS_SIZE = 5;
    public static final int MIN_PASSWORD_LENGTH = 10;
    public static final int MAX_PASSWORD_LENGTH = 32;
    public static final int CUSTOMER_ID_LENGTH = 10;

    @Id
    private String customerId;

    private String keyHash;

    private String currentPasswordMask;

    @OneToMany(mappedBy = "id.customerId", cascade = CascadeType.MERGE)
    private Set<CustomerSecret> secrets;

}
