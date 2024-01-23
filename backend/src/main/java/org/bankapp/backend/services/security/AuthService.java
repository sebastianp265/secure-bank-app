package org.bankapp.backend.services.security;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.algorithms.PartialPasswordProcessor;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.bankapp.backend.repostiories.security.CustomerCredentialsRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.KeyAndSecrets;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionService sessionService;

    private final PartialPasswordProcessor passwordProcessor;

    private final CustomerCredentialsRepository customerCredentialsRepository;

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

    void authenticate(String customerId, Map<Integer, Character> passwordParts) {
        KeyAndSecrets keyAndSecrets = getKeyAndSecrets(customerId);
        if(!passwordProcessor.isKeyValid(passwordParts, keyAndSecrets)) {
            throw new InvalidUserIdOrPasswordException();
        }
    }

    private KeyAndSecrets getKeyAndSecrets(String customerId) {
        CustomerCredentials customerCredentials = customerCredentialsRepository.findById(customerId)
                .orElseThrow(InvalidUserIdOrPasswordException::new);

        Map<Integer, BigInteger> secrets = customerCredentials.getSecrets().stream()
                .collect(Collectors.toMap(c -> c.getId().getSecretIndex(), CustomerSecret::getSecret));
        List<BigInteger> secretsList = secrets.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
        return new KeyAndSecrets(customerCredentials.getKeyHash(), secretsList);
    }
}