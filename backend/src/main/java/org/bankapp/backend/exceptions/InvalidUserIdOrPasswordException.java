package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidUserIdOrPasswordException extends AbstractCustomRuntimeException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE = "Invalid user id or password";

    public InvalidUserIdOrPasswordException() {
        super(HTTP_STATUS, MESSAGE);
    }

}
