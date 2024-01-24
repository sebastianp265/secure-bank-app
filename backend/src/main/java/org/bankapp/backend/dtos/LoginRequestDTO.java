package org.bankapp.backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.validation.Patterns;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class LoginRequestDTO {

    @NotNull(message = "")
    String customerId;

    @NotNull(message = "")
    @Pattern(regexp = Patterns.ALLOWED_PASSWORD_WITH_MASK,
            message = "")
    String password;
}
