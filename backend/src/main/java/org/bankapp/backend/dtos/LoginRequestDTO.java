package org.bankapp.backend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.dtos.validation.Patterns;

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
