package org.bankapp.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class TransferRequestDTO {

    @NotNull(message = "")
    @Size(min = 32, max = 32, message = "'From account' number is invalid")
    String fromAccount;

    @NotEmpty(message = "Provide recipient full name")
    String recipientFullName;

    @NotNull(message = "")
    @Pattern(regexp = "^\\d{26}$", message = "Invalid account number")
    String recipientAccountNumber;

    @NotNull(message = "")
    @Size(max = 256, message = "Title cannot be longer than 255 characters")
    String title;

    @NotNull(message = "Fill amount to transfer")
    @Pattern(regexp = "^\\d+(.\\d{1,2})?$", message = "Invalid amount format")
    String amount;
}
