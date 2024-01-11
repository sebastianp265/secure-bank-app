package org.bankapp.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.repostiories.CustomerCredentialsRepository;
import org.bankapp.backend.repostiories.CustomerSecretRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.BigInteger.valueOf;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerCredentialsRepository customerCredentialsRepository;

    private final CustomerSecretRepository customerSecretRepository;

    private final SecureRandom random = new SecureRandom();

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void changePassword(String changePasswordToken, Map<Integer, Character> passwordParts, String newPassword) {
        String exampleCustomerId = "1234567890"; // in the future, gotten from security context
        if (!isPasswordCorrect(exampleCustomerId, passwordParts)) {
            // TODO: Change runtime exception to api custom exception
            throw new RuntimeException("Password is incorrect");
        }
        if (!isResetTokenValid(exampleCustomerId, changePasswordToken)) {
            // TODO: Change runtime exception to api custom exception
            throw new RuntimeException("Reset token is invalid");
        }

        CustomerCredentials customerCredentials = customerCredentialsRepository
                .findById(exampleCustomerId)
                .orElseThrow(); // TODO: Change to custom exception

        long[] polynomialCoefficients = random.longs(CustomerCredentials.REQUIRED_PASSWORD_PARTS).toArray();
        List<BigInteger> secrets = new ArrayList<>(CustomerCredentials.MAX_PASSWORD_LENGTH);
        // f(x) = a0 + a1 * x + a2 * x^2 + ... + an * x^n, where 'a' is polynomialCoefficients
        // a0 is secret key which will be stored in database as its hash value
        // secrets are f(1), f(2), ..., f(n) which are also stored in database
        for (int i = 0; i < newPassword.length(); i++) {
            int x = i + 1;
            BigInteger polynomialValueAtX = valueOf(0);
            for (int j = 0; j < polynomialCoefficients.length; j++) {
                polynomialValueAtX = polynomialValueAtX.add(valueOf(polynomialCoefficients[j])
                        .multiply(valueOf(x).pow(j)));
            }

            secrets.add(polynomialValueAtX.subtract(valueOf(newPassword.charAt(i))));
        }

        // map secrets to CustomerSecret entities
        Set<CustomerSecret> customerSecrets = IntStream.range(0, secrets.size())
                .mapToObj(i -> CustomerSecret.builder()
                        .id(CustomerSecret.CustomerSecretId.builder()
                                .customerId(exampleCustomerId)
                                .secretIndex(i)
                                .build())
                        .secret(secrets.get(i))
                        .build())
                .collect(Collectors.toSet());

        if (customerSecretRepository.existsByIdCustomerId(exampleCustomerId)) {
            customerSecretRepository.deleteAllByIdCustomerId(exampleCustomerId);
        }

        customerSecretRepository.saveAll(customerSecrets);

        long salt = random.nextLong(); // in my opinion unnecessary, but it is in the assignment
        // TODO: Ask if is it necessary to use salt in this case
        long key = polynomialCoefficients[0] + salt;

        customerCredentials.setSalt(salt);
        customerCredentials.setKeyHash(passwordEncoder.encode(Long.toString(key)));
        customerCredentials.setSecrets(customerSecrets);

        customerCredentialsRepository.save(customerCredentials);
    }

    private boolean isPasswordCorrect(String customerId, Map<Integer, Character> passwordParts) {

        return true;
    }

    private boolean isResetTokenValid(String customerId, String resetToken) {
        // TODO: implement checking if the reset token is valid
        return true;
    }
}
