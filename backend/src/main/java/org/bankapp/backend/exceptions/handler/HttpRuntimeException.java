package org.bankapp.backend.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class HttpRuntimeException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;
}
