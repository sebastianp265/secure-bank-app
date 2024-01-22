package org.bankapp.backend.services.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.entities.security.CustomerSession;
import org.bankapp.backend.exceptions.SessionExpiredException;
import org.bankapp.backend.repostiories.CustomerSessionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    public static final String SESSION_COOKIE_NAME = "sessionId";
    public static final int COOKIE_EXPIRATION_TIME_SECONDS = 15 * 60;

    private final CustomerSessionRepository customerSessionRepository;

    public String authorizeCustomer(String sessionId) {
        try {
            return customerSessionRepository
                    .findById(UUID.fromString(sessionId))
                    .map(CustomerSession::getCustomerId)
                    .orElseThrow();
        } catch (Exception e) {
            throw new SessionExpiredException();
        }
    }

    public boolean isSessionIdValid(UUID sessionId) {
        return customerSessionRepository.existsById(sessionId);
    }

    @Transactional
    public UUID createSession(String customerId) {
        customerSessionRepository.deleteAllByCustomerId(customerId);
        CustomerSession customerSession = CustomerSession.builder()
                .customerId(customerId)
                .build();
        CustomerSession createdSession = customerSessionRepository.save(customerSession);
        return createdSession.getSessionId();
    }
}
