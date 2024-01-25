package org.bankapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class CardPreviewGetDTO {

    String id;
    String cardNumber;
    String cvvCode;
    String validThru;

}
