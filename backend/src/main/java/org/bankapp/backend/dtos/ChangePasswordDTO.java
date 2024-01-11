package org.bankapp.backend.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@Builder
@Jacksonized
public class ChangePasswordDTO {

    Map<Integer, Character> passwordParts;

    String newPassword;
}
