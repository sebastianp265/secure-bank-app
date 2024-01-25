package org.bankapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CustomerInfoPreviewGetDTO {
    String fullName;
    String email;
}