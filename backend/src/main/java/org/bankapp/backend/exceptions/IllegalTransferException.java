package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class IllegalTransferException extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE = "";

    public IllegalTransferException() {
        super(HTTP_STATUS, MESSAGE);
    }

}
