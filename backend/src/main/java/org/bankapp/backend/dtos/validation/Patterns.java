package org.bankapp.backend.dtos.validation;

import org.bankapp.backend.entities.security.CustomerCredentials;

public class Patterns {

    private Patterns() {
    }

    private static final String ALLOWED_PASSWORD_CHARACTERS = "A-Za-z0-9!@#$%^&*()_+}{|\":?><~/.,';\\]\\[=\\-`";

    public static final String ALLOWED_PASSWORD_CHARACTERS_REGEX = "^["
            + ALLOWED_PASSWORD_CHARACTERS
            + "]{" + CustomerCredentials.MIN_PASSWORD_LENGTH + "," + CustomerCredentials.MAX_PASSWORD_LENGTH + "}$";

    public static final String ALLOWED_PASSWORD_WITH_MASK = "^["
            + ALLOWED_PASSWORD_CHARACTERS + " "
            + "]{" + CustomerCredentials.MAX_PASSWORD_LENGTH + "}$";
}
