package org.bankapp.backend.algorithms;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.bankapp.backend.entities.security.CustomerCredentials;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.math.BigInteger.valueOf;
import static org.bankapp.backend.entities.security.CustomerCredentials.REQUIRED_PASSWORD_PARTS_SIZE;

@Component
public class PartialPasswordProcessor {
    private static final int NUMBER_OF_HASH_ITERATIONS = 12;
    private final SecureRandom random = new SecureRandom();
    private final BCrypt.Hasher passwordHasher = BCrypt.withDefaults();
    private final BCrypt.Verifyer passwordVerifyer = BCrypt.verifyer();

    // implementation is based on Shamir's Secret Sharing algorithm
    // https://en.wikipedia.org/wiki/Shamir%27s_Secret_Sharing
    public KeyAndSecrets generateKeyAndSecrets(CharSequence newPassword) {
        long[] polynomialCoefficients = random.longs(REQUIRED_PASSWORD_PARTS_SIZE).toArray();

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

        String keyHash = passwordHasher.hashToString(NUMBER_OF_HASH_ITERATIONS, Long.toString(polynomialCoefficients[0]).toCharArray());
        return new KeyAndSecrets(keyHash, secrets);
    }

    public boolean isKeyValid(Map<Integer, Character> passwordParts, KeyAndSecrets keyAndSecrets) {
        List<BigInteger> allSecrets = keyAndSecrets.secrets;
        List<Pair<Integer, BigInteger>> knownPoints = new ArrayList<>(REQUIRED_PASSWORD_PARTS_SIZE);
        for(Map.Entry<Integer, Character> entry : passwordParts.entrySet()) {
            int charIndex = entry.getKey();
            Integer x = charIndex + 1;
            BigInteger y = allSecrets.get(charIndex).add(valueOf(entry.getValue()));
            knownPoints.add(Pair.of(x, y));
        }

        String key = reconstructKey(knownPoints);
        return passwordVerifyer.verify(key.toCharArray(), keyAndSecrets.keyHash).verified;
    }

    private String reconstructKey(List<Pair<Integer, BigInteger>> knownPoints) {
        List<BigInteger> numerators = new ArrayList<>(REQUIRED_PASSWORD_PARTS_SIZE);
        List<BigInteger> denominators = new ArrayList<>(REQUIRED_PASSWORD_PARTS_SIZE);
        for (int j = 0; j < REQUIRED_PASSWORD_PARTS_SIZE; j++) {
            BigInteger numerator = knownPoints.get(j).getSecond();
            BigInteger denominator = valueOf(1);
            for (int m = 0; m < REQUIRED_PASSWORD_PARTS_SIZE; m++) {
                if (m == j) {
                    continue;
                }
                numerator = numerator.multiply(
                        valueOf(knownPoints.get(m).getFirst()));
                denominator = denominator.multiply(
                        valueOf(
                                knownPoints.get(m).getFirst().longValue() - knownPoints.get(j).getFirst().longValue()
                        )
                );
            }
            numerators.add(numerator);
            denominators.add(denominator);
        }

        BigInteger key = sumFractions(numerators, denominators);

        return String.valueOf(key);
    }

    private BigInteger sumFractions(List<BigInteger> numerators, List<BigInteger> denominators) {
        BigInteger resultNumerator = numerators.getFirst();
        BigInteger resultDenominator = denominators.getFirst();
        for (int i = 1; i < numerators.size(); i++) {
            BigInteger nextNumerator = numerators.get(i);
            BigInteger nextDenominator = denominators.get(i);

            resultNumerator = resultNumerator.multiply(nextDenominator)
                    .add(nextNumerator.multiply(resultDenominator));
            resultDenominator = resultDenominator.multiply(nextDenominator);
        }

        return resultNumerator.divide(resultDenominator);
    }

    public record KeyAndSecrets(String keyHash, List<BigInteger> secrets) {
    }

}
