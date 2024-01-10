package org.bankapp.backend.entities.security;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class CustomerCredentials {

    @Id
    private String customerId;

    private Long key;

    private String salt;

    @OneToMany(mappedBy = "id.customerId")
    private Set<CustomerSecret> secrets;

}
