package org.bankapp.backend.services.security;

import lombok.AllArgsConstructor;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.bankapp.backend.exceptions.InvalidUserIdOrPasswordException;
import org.bankapp.backend.repostiories.CustomerCredentialsRepository;
import org.bankapp.backend.repostiories.CustomerSecretRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.bankapp.backend.algorithms.PartialPasswordProcessor.KeyAndSecrets;

@Service
@AllArgsConstructor
public class CustomerCredentialsService {

    private final CustomerCredentialsRepository customerCredentialsRepository;

    private final CustomerSecretRepository customerSecretRepository;

    public KeyAndSecrets getKeyAndSecrets(String customerId) {
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

    protected void saveKeyAndSecrets(String customerId, KeyAndSecrets keyAndSecrets) {
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
