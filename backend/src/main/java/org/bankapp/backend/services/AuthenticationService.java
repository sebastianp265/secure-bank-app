package org.bankapp.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bankapp.backend.algorithms.PartialPasswordProcessor;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.bankapp.backend.repostiories.CustomerCredentialsRepository;
import org.bankapp.backend.repostiories.CustomerSecretRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final CustomerCredentialsRepository customerCredentialsRepository;

    private final CustomerSecretRepository customerSecretRepository;

    private final PartialPasswordProcessor partialPasswordProcessor;

    @Transactional
    public void changePassword(String changePasswordToken, Map<Integer, Character> passwordParts, String newPassword) {
        String exampleCustomerId = "1234567890"; // in the future, gotten from security context
        if (!isPasswordCorrect(exampleCustomerId, passwordParts)) {
            throw new InvalidUserIdOrPasswordException();
        }
        if (!isResetTokenValid(exampleCustomerId, changePasswordToken)) {
            // TODO: Change runtime exception to api custom exception
            throw new RuntimeException("Reset token is invalid");
        }

        KeyAndSecrets keyAndSecrets = partialPasswordProcessor
                .generateKeyAndSecrets(newPassword);

        saveKeyAndSecrets(exampleCustomerId, keyAndSecrets);
    }

    private void saveKeyAndSecrets(String customerId, KeyAndSecrets keyAndSecrets) {
        CustomerCredentials customerCredentials = customerCredentialsRepository
                .findById(customerId)
                .orElseThrow(InvalidUserIdOrPasswordException::new);

        // map secrets to CustomerSecret entities
        List<BigInteger> secrets = keyAndSecrets.secrets();
        Set<CustomerSecret> customerSecrets = IntStream.range(0, secrets.size())
                .mapToObj(i -> CustomerSecret.builder()
                        .id(CustomerSecret.CustomerSecretId.builder()
                                .customerId(customerId)
                                .secretIndex(i)
                                .build())
                        .secret(secrets.get(i))
                        .build())
                .collect(Collectors.toSet());

        replaceCustomerSecrets(customerId, customerSecrets);

        updateCustomerCredentials(customerCredentials, keyAndSecrets.key(), customerSecrets);
    }

    private void replaceCustomerSecrets(String customerId, Set<CustomerSecret> customerSecrets) {
        customerSecretRepository.deleteAllByIdCustomerId(customerId);
        customerSecretRepository.saveAll(customerSecrets);
    }

    private void updateCustomerCredentials(CustomerCredentials customerCredentials, long key, Set<CustomerSecret> customerSecrets) {
        customerCredentials.setKeyHash(passwordEncoder.encode(Long.toString(key)));
        customerCredentials.setSecrets(customerSecrets);

        customerCredentialsRepository.save(customerCredentials);
    }

    private boolean isPasswordCorrect(String customerId, Map<Integer, Character> passwordParts) {
        CustomerCredentials customerCredentials = customerCredentialsRepository
                .findById(customerId).orElseThrow();

        BigInteger key = partialPasswordProcessor
                .reconstructKey(passwordParts, customerCredentials.getSecrets());

        return passwordEncoder.matches(key.toString(), customerCredentials.getKeyHash());
    }

    private boolean isResetTokenValid(String customerId, String resetToken) {
        // TODO: implement checking if the reset token is valid
        return true;
    }
}