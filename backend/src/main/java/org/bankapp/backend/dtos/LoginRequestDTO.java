package org.bankapp.backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.entities.security.CustomerCredentials;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class LoginRequestDTO {

    @NotNull(message = "")
    @Pattern(regexp = "^\\d{10}$",
            message = "Customer ID must be 10 digits long.")
    String customerId;

    @NotNull(message = "")
    @Size(min = CustomerCredentials.REQUIRED_PASSWORD_PARTS,
            max = CustomerCredentials.REQUIRED_PASSWORD_PARTS,
            message = "All password parts must be provided.")
    Map<Integer, Character> passwordParts;
}
