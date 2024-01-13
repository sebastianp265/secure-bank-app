package org.bankapp.backend.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidUserIdOrPasswordException(HttpRuntimeException exception) {
        return ResponseEntity.status(exception.getHttpStatus().value())
                .body(
                        new ErrorResponse(exception.getHttpStatus().value(),
                                exception.getMessage())
                );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception) {
        log.debug("Unhandled exception: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "")
                );
    }

}
