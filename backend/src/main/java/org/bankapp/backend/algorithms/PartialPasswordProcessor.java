package org.bankapp.backend.algorithms;

import org.bankapp.backend.entities.security.CustomerCredentials;
import org.bankapp.backend.entities.security.CustomerSecret;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.math.BigInteger.valueOf;

@Component
public class PartialPasswordProcessor {
    private final SecureRandom random = new SecureRandom();

    public KeyAndSecrets generateKeyAndSecrets(CharSequence newPassword) {
        long[] polynomialCoefficients = random.longs(CustomerCredentials.REQUIRED_PASSWORD_PARTS).toArray();

        List<BigInteger> secrets = new ArrayList<>(CustomerCredentials.MAX_PASSWORD_LENGTH);
        // f(x) = a0 + a1 * x + a2 * x^2 + ... + an * x^n, where 'a' is polynomialCoefficients
        // a0 is secret key which will be stored in database as its hash value
        // secrets are f(1), f(2), ..., f(n) which are also stored in database
        for (int i = 0; i < newPassword.length(); i++) {
            int x = i + 1;
            BigInteger polynomialValueAtX = valueOf(0);
            for (int j = 0; j < polynomialCoefficients.length; j++) {
                polynomialValueAtX = polynomialValueAtX.add(valueOf(polynomialCoefficients[j]).multiply(valueOf(x).pow(j)));
            }

            secrets.add(polynomialValueAtX.subtract(valueOf(newPassword.charAt(i))));
        }

        return new KeyAndSecrets(polynomialCoefficients[0], secrets);
    }

    public BigInteger reconstructKey(Map<Integer, Character> passwordParts, Set<CustomerSecret> customerSecrets) {
        BigInteger key = valueOf(0);
        for (Map.Entry<Integer, Character> entry : passwordParts.entrySet()) {
            int charIndex = entry.getKey();
            char charValue = entry.getValue();

            BigInteger polynomialValue = customerSecrets.stream()
                    .filter(customerSecret -> customerSecret.getId().getSecretIndex() == charIndex)
                    .map(CustomerSecret::getSecret)
                    .findAny()
                    .orElseThrow()
                    .add(valueOf(charValue));

            BigInteger nominator = valueOf(1);
            BigInteger denominator = valueOf(1);
            for (int m = 0; m < CustomerCredentials.REQUIRED_PASSWORD_PARTS; m++) {
                if (m != entry.getKey()) {
                    nominator = nominator.multiply(valueOf(m + 1L));
                    denominator = denominator.multiply(valueOf((long) entry.getKey() - m));
                }
            }

            key = key.add(polynomialValue.multiply(nominator).divide(denominator));
        }

        return key;
    }

    public record KeyAndSecrets(long key, List<BigInteger> secrets) {
    }


}
