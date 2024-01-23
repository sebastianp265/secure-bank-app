package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class RecipientAccountNotFoundException extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    private static final String MESSAGE = "Provided recipient account number doesn't exist in our bank.";

    public RecipientAccountNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
