package org.bankapp.backend.services.security;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.algorithms.PartialPasswordProcessor;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.KeyAndSecrets;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PartialPasswordProcessor partialPasswordProcessor;

    private final SessionService sessionService;

    private final PartialPasswordProcessor passwordProcessor;

    private final CustomerCredentialsService customerCredentialsService;

    @Transactional
    public void changePassword(String sessionId,
                               Map<Integer, Character> passwordParts,
                               String newPassword) {
        String customerId = sessionService.authorizeCustomer(sessionId);
        authenticate(customerId, passwordParts);

        KeyAndSecrets keyAndSecrets = partialPasswordProcessor
                .generateKeyAndSecrets(newPassword);

        customerCredentialsService.saveKeyAndSecrets(customerId, keyAndSecrets);
    }

    public void login(String customerId, Map<Integer, Character> passwordParts, HttpServletResponse response) {
        authenticate(customerId, passwordParts);
        UUID sessionId = sessionService.createSession(customerId);

        ResponseCookie responseCookie = ResponseCookie.from(SessionService.SESSION_COOKIE_NAME, sessionId.toString())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(SessionService.COOKIE_EXPIRATION_TIME_SECONDS)
                .build();

        response.addHeader("Set-Cookie", responseCookie.toString());
    }

    private void authenticate(String customerId, Map<Integer, Character> passwordParts) {
        KeyAndSecrets keyAndSecrets = customerCredentialsService.getKeyAndSecrets(customerId);
        if(!passwordProcessor.isKeyValid(passwordParts, keyAndSecrets)) {
            throw new InvalidUserIdOrPasswordException();
        }
    }
}