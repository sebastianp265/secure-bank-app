package org.bankapp.backend.exceptions.handler;

public record ErrorResponse(int statusCode, String message) {
}
