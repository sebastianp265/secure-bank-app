package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.HttpRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidUserIdOrPasswordException extends HttpRuntimeException {
    private static final String MESSAGE = "Invalid user id or password";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public InvalidUserIdOrPasswordException() {
        super(HTTP_STATUS, MESSAGE);
    }

}
