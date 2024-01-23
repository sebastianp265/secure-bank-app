package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class SessionExpiredException extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "Session has expired. Please log in again.";

    public SessionExpiredException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
