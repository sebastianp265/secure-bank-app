package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class IllegalTransferHistoryRequest extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;
    private static final String MESSAGE = "";

    public IllegalTransferHistoryRequest() {
        super(HTTP_STATUS, MESSAGE);
    }
}
