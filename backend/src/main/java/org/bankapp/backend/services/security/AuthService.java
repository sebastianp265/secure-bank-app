package org.bankapp.backend.services.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.algorithms.PartialPasswordProcessor;
import org.bankapp.backend.dtos.RequestLoginResponseDTO;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.bankapp.backend.repostiories.security.CustomerCredentialsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.KeyAndSecrets;
import static org.bankapp.backend.entities.security.CustomerCredentials.MAX_PASSWORD_LENGTH;
import static org.bankapp.backend.entities.security.CustomerCredentials.MIN_PASSWORD_LENGTH;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final SessionService sessionService;
    private final PartialPasswordProcessor passwordProcessor;
    private final CustomerCredentialsRepository customerCredentialsRepository;
    SecureRandom random = new SecureRandom();

    public RequestLoginResponseDTO requestLogin(String customerId) {
        try {
            CustomerCredentials customerCredentials = customerCredentialsRepository.findById(customerId)
                    .orElseThrow();
            if (customerCredentials.getCurrentPasswordMask() != null) {
                return RequestLoginResponseDTO.builder()
                        .passwordMask(customerCredentials.getCurrentPasswordMask())
                        .build();
            }
            String passwordMask = generatePasswordMask(customerCredentials.getSecrets().size());
            customerCredentials.setCurrentPasswordMask(passwordMask);
            customerCredentialsRepository.save(customerCredentials);
            return RequestLoginResponseDTO.builder()
                    .passwordMask(passwordMask)
                    .build();
        } catch (NoSuchElementException e) {
            int fakePasswordMaskLength = MAX_PASSWORD_LENGTH / 2; // Most common password length prediction
            String passwordMask = generatePasswordMask(fakePasswordMaskLength);
            return RequestLoginResponseDTO.builder()
                    .passwordMask(passwordMask)
                    .build();
        }
    }

    private String generatePasswordMask(int n) {
        List<Integer> possibleCharacters = new ArrayList<>(MAX_PASSWORD_LENGTH);
        for (int i = 0; i < n; i++) {
            possibleCharacters.add(i);
        }
        Collections.shuffle(possibleCharacters, random);
        StringBuilder stringBuilder = new StringBuilder("x".repeat(MAX_PASSWORD_LENGTH));
        for (int i = 0; i < CustomerCredentials.REQUIRED_PASSWORD_PARTS_SIZE; i++) {
            stringBuilder.setCharAt(possibleCharacters.get(i), '+');
        }

        return stringBuilder.toString();
    }

    public void login(String customerId, String password, HttpServletResponse response) {
        authenticate(customerId, password);
        String sessionId = sessionService.createSession(customerId);
        response.addHeader("Set-Cookie", sessionService.generateCookie(sessionId));
    }

    void authenticate(String customerId, String password) {
        CustomerCredentials customerCredentials = customerCredentialsRepository.findById(customerId)
                .orElseThrow(InvalidUserIdOrPasswordException::new);
        if(isProvidedPasswordInvalid(password, customerCredentials.getCurrentPasswordMask())) {
            throw new InvalidUserIdOrPasswordException();
        }
        Map<Integer, Character> passwordParts = parsePasswordToPasswordParts(password);
        KeyAndSecrets keyAndSecrets = getKeyAndSecrets(customerId);
        if (!passwordProcessor.isKeyValid(passwordParts, keyAndSecrets)) {
            throw new InvalidUserIdOrPasswordException();
        }
        customerCredentials.setCurrentPasswordMask(null);
        customerCredentialsRepository.save(customerCredentials);
    }

    private Map<Integer, Character> parsePasswordToPasswordParts(String password) {
        Map<Integer, Character> passwordParts = HashMap.newHashMap(CustomerCredentials.REQUIRED_PASSWORD_PARTS_SIZE);
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) != ' ') {
                passwordParts.put(i, password.charAt(i));
            }
        }
        return passwordParts;
    }

    private boolean isProvidedPasswordInvalid(String providedPassword, String passwordMask) {
        if(passwordMask == null) {
            return true;
        }
        Assert.isTrue(providedPassword.length() == passwordMask.length(),
                "Password and mask lengths do not match");
        for(int i = 0; i < passwordMask.length(); i++) {
            if (passwordMask.charAt(i) == '+' && providedPassword.charAt(i) == ' ') {
                return true;
            }
            if (passwordMask.charAt(i) == 'x' && providedPassword.charAt(i) != ' ') {
                return true;
            }
        }
        return false;
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