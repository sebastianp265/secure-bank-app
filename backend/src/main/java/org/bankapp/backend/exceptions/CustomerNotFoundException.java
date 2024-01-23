package org.bankapp.backend.exceptions;

import org.bankapp.backend.exceptions.handler.AbstractCustomRuntimeException;
import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends AbstractCustomRuntimeException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "Your account doesn't exist. Please contact with bank support.";

    public CustomerNotFoundException(){
        super(HTTP_STATUS, MESSAGE);
    }
}
