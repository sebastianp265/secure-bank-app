package org.bankapp.backend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.bankapp.backend.dtos.validation.Patterns;

@Value
@Builder
@Jacksonized
public class ChangePasswordDTO {

    @NotNull(message = "")
    @Pattern(regexp = Patterns.ALLOWED_PASSWORD_WITH_MASK,
            message = "")
    String password;

    @NotBlank(message = "Password must not be empty.")
    @Pattern(regexp = Patterns.ALLOWED_PASSWORD_CHARACTERS_REGEX,
            message = "Password can contain only letters, digits and special characters: !@#$%^&*()_+}{|\":?><~/.,';[]=-`")
    String newPassword;
}
