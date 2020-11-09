package fi.morabotti.skydive.domain;

import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.exception.PasswordException;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Session;
import fi.morabotti.skydive.security.Password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.util.UUID;

@Singleton
public class AccountDomain {
    private static final int ITERATIONS = 9420;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    @Inject
    public AccountDomain() {
    }

    public Session createSession(
            UUID token,
            Instant validUntil,
            Account account
    ) {
        return Session.builder()
                .setId(0L)
                .setToken(token)
                .setValidUntil(validUntil)
                .setAccount(account)
                .build();
    }

    public Account createAccount(String username, Password password) {
        return Account.builder()
                .setId(0L)
                .setDeletedAt(null)
                .setUsername(username)
                .setPasswordHash(password.getHash())
                .setPasswordSalt(password.getSalt())
                .setAccountRole(AccountRole.admin)
                .build();
    }

    public boolean validatePassword(Account account, String passwordString) {
        try {
            byte[] testHash = getHashed(passwordString.toCharArray(), account.getPasswordSalt());
            byte[] accountPasswordHash = account.getPasswordHash();
            if (testHash.length != accountPasswordHash.length) {
                return false;
            }

            for (int i = 0; i < testHash.length; i++) {
                if (testHash[i] != accountPasswordHash[i]) {
                    return false;
                }
            }
            return true;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Password generatePassword(String passwordString) {
        try {
            byte[] salt = generateSalt();

            return Password.builder()
                    .setHash(getHashed(passwordString.toCharArray(), salt))
                    .setSalt(salt)
                    .build();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new PasswordException("Could not create password");
        }
    }

    public byte[] generateSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            return salt;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new PasswordException("Could not generate salt");
        }
    }

    private byte[] getHashed(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return SecretKeyFactory.getInstance(ALGORITHM)
                .generateSecret(
                        new PBEKeySpec(
                                password,
                                salt,
                                ITERATIONS,
                                KEY_LENGTH
                        )
                ).getEncoded();
    }
}
