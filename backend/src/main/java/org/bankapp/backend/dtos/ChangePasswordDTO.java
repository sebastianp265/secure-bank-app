package org.bankapp.backend.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.entities.security.CustomerCredentials;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class ChangePasswordDTO {

    @NotNull(message = "")
    @Size(min = CustomerCredentials.REQUIRED_PASSWORD_PARTS,
            max = CustomerCredentials.REQUIRED_PASSWORD_PARTS,
            message = "All password parts must be provided.")
    Map<Integer, Character> passwordParts;

    @NotBlank(message = "Password must not be empty.")
    @Size(min = CustomerCredentials.MIN_PASSWORD_LENGTH,
            max = CustomerCredentials.MAX_PASSWORD_LENGTH,
            message = "Password must be between from {min} to {max} characters long.")
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()_+}{|\":?><~/.,';\\]\\[=\\-`]*$",
            message = "Password can contain only letters, digits and special characters: !@#$%^&*()_+}{|\":?><~/.,';[]=-`")
    String newPassword;
}
