package org.bankapp.backend.services.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.SessionIdGeneratorBase;
import org.apache.catalina.util.StandardSessionIdGenerator;
import org.bankapp.backend.entities.security.CustomerSession;
import org.bankapp.backend.exceptions.SessionExpiredException;
import org.bankapp.backend.repostiories.security.CustomerSessionRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    public static final String SESSION_COOKIE_NAME = "sessionId";
    public static final int COOKIE_EXPIRATION_TIME_SECONDS = 15 * 60;

    private final CustomerSessionRepository customerSessionRepository;

    private final StandardSessionIdGenerator sessionIdGenerator = new StandardSessionIdGenerator();

    public String authorizeCustomer(String sessionId) {
        try {
            return customerSessionRepository
                    .findById(sessionId)
                    .map(CustomerSession::getCustomerId)
                    .orElseThrow();
        } catch (Exception e) {
            throw new SessionExpiredException();
        }
    }

    @Transactional
    public String createSession(String customerId) {
        customerSessionRepository.deleteAllByCustomerId(customerId);
        String sessionId = sessionIdGenerator.generateSessionId();
        while(customerSessionRepository.existsById(sessionId)) {
            sessionId = sessionIdGenerator.generateSessionId();
        }
        CustomerSession customerSession = CustomerSession.builder()
                .sessionId(sessionIdGenerator.generateSessionId())
                .customerId(customerId)
                .build();
        CustomerSession createdSession = customerSessionRepository.save(customerSession);
        return createdSession.getSessionId();
    }

    public String generateCookie(String sessionId) {
        return ResponseCookie.from(SESSION_COOKIE_NAME, sessionId)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(COOKIE_EXPIRATION_TIME_SECONDS)
                .build()
                .toString();
    }

    void deleteSession(String sessionId) {
        customerSessionRepository.deleteById(sessionId);
    }
}
