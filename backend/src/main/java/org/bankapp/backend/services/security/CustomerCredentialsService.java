package org.bankapp.backend.services.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bankapp.backend.algorithms.PartialPasswordProcessor;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.bankapp.backend.repostiories.security.CustomerCredentialsRepository;
import org.bankapp.backend.repostiories.security.CustomerSecretRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.KeyAndSecrets;

@Service
@AllArgsConstructor
public class CustomerCredentialsService {

    private final CustomerCredentialsRepository customerCredentialsRepository;
    private final CustomerSecretRepository customerSecretRepository;

    private final SessionService sessionService;
    private final AuthService authService;

    private final PartialPasswordProcessor passwordProcessor;

    @Transactional
    public void changePassword(String customerId,
                               String password,
                               String newPassword) {
        authService.authenticate(customerId, password);

        KeyAndSecrets keyAndSecrets = passwordProcessor
                .generateKeyAndSecrets(newPassword);

        saveKeyAndSecrets(customerId, keyAndSecrets);
    }

    @Transactional
    public void saveKeyAndSecrets(String customerId, KeyAndSecrets keyAndSecrets) {
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

        customerSecretRepository.deleteAllByIdCustomerId(customerId);
        customerSecretRepository.saveAll(customerSecrets);

        customerCredentials.setKeyHash(keyAndSecrets.keyHash());
        customerCredentials.setSecrets(customerSecrets);

        customerCredentialsRepository.save(customerCredentials);
    }

}
