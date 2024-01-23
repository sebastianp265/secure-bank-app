package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class NoSufficientFundsOnAccountException extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    private static final String MESSAGE = "No sufficient funds exist on your provided account number";

    public NoSufficientFundsOnAccountException() {
        super(HTTP_STATUS, MESSAGE);
    }

}
