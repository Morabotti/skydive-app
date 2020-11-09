package fi.morabotti.skydive.controller;

import fi.morabotti.skydive.dao.AccountDao;
import fi.morabotti.skydive.dao.SessionDao;
import fi.morabotti.skydive.domain.AccountDomain;
import fi.morabotti.skydive.exception.AuthenticationException;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Session;
import fi.morabotti.skydive.view.AccountView;
import fi.morabotti.skydive.view.TokenResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

@Singleton
public class AccountController {
    private final AccountDomain accountDomain;

    private final AccountDao accountDao;
    private final SessionDao sessionDao;

    private static final Integer CACHE_SIZE = 50;
    private static final Integer TOKEN_LIFETIME_HOURS = 1;

    private final LinkedBlockingQueue<Session> sessionCache;

    @Inject
    public AccountController(
            AccountDomain accountDomain,
            AccountDao accountDao,
            SessionDao sessionDao
    ) {
        this.accountDomain = accountDomain;
        this.accountDao = accountDao;
        this.sessionDao = sessionDao;

        this.sessionCache = new LinkedBlockingQueue<>();
    }

    /**
     * Performs authentication with session token.
     * @param tokenString Session token as string
     * @return Optional account that the token belongs to
     *          Optional empty if token is not valid
     * */
    public Optional<Account> authenticate(String tokenString) {
        try {
            UUID token = UUID.fromString(tokenString);

            Optional<Session> optionalSession = getSessionByToken(token);

            if (!optionalSession.isPresent()) {
                return Optional.empty();
            }

            Session session = updateSession(optionalSession.get());

            persistOldestSession();

            return Optional.of(session.getAccount());
        }
        catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    /**
     * Authenticates user with username and password.
     * @param username Username used to log in to system
     * @param password Password used to log in to system
     * @return TokenResponse with newly created session token
     * @throws AuthenticationException if credentials do not match
     * */
    public TokenResponse authenticate(String username, String password) {
        Account account = accountDao.findAccountByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Authentication failed."));

        if (accountDomain.validatePassword(account, password)) {
            return TokenResponse.builder()
                    .setToken(createOrUpdateSession(account).getToken())
                    .setUser(AccountView.of(account))
                    .build();
        }

        throw new AuthenticationException("Authentication failed.");
    }

    /**
     * Performs logout.
     * @param tokenString Session token as string
     * @return boolean Boolean whether logout is successful
     * */
    public Boolean logout(String tokenString) {
        UUID token = UUID.fromString(tokenString);

        Optional<Session> optionalSession = getSessionByToken(token);

        if (!optionalSession.isPresent()) {
            return false;
        }

        sessionCache.remove(optionalSession.get());
        sessionDao.deleteByToken(tokenString);
        return true;
    }

    /**
     * Performs authentication with session token.
     * @param tokenString Session token as string
     * @return Optional TokenResponse
     * */
    public TokenResponse authenticateWithToken(String tokenString) {
        UUID token = UUID.fromString(tokenString);

        Optional<Session> optionalSession = getSessionByToken(token);

        if (!optionalSession.isPresent()) {
            throw new AuthenticationException("Token is expired or invalid.");
        }

        Session session = updateSession(optionalSession.get());

        persistOldestSession();

        return TokenResponse.builder()
                .setToken(session.getToken())
                .setUser(AccountView.of(session.getAccount()))
                .build();
    }

    /**
     * Fetches session either from cache or from persistent storage.
     * @param token provided token to search for
     * @return Optional session. Present if fount from cache or persistent storage.
     *          Empty if not found.
     * */
    private Optional<Session> getSessionByToken(UUID token) {
        return Optional.ofNullable(sessionCache
                .stream()
                .filter(s -> s.getToken().equals(token))
                .filter(s -> s.getValidUntil().isAfter(Instant.now()))
                .findFirst()
                .orElseGet(
                        () -> sessionDao.findValidByToken(token.toString())
                                .map(session -> session
                                        .toBuilder()
                                        .setAccount(session.getAccount())
                                        .build()
                                )
                                .orElse(null)));
    }

    /**
     * Creates or updates session for provided account.
     * If valid session is found from persistent storage,
     * it is added to cache. If not, a new one is created
     * to cache and to persistent storage.
     * @param account Account to create or update the session for.
     * @return Session that has been created or updated      .
     * */
    private Session createOrUpdateSession(Account account) {
        persistOldestSession();

        Instant newExpiryTime = Instant.now().plus(
                TOKEN_LIFETIME_HOURS,
                ChronoUnit.HOURS
        );

        Session session = sessionDao.findValidByAccountId(account.getId())
                .map(s -> s.toBuilder().setValidUntil(newExpiryTime).build())
                .orElseGet(() ->  sessionDao.create(accountDomain
                        .createSession(
                                UUID.randomUUID(),
                                newExpiryTime,
                                account
                        )));

        return addSession(
                session
                        .toBuilder()
                        .setAccount(session.getAccount())
                        .build()
        );
    }

    /**
     * Persists oldest session from cache.
     * */
    private void persistOldestSession() {
        if (sessionCache.size() >= CACHE_SIZE) {
            Session session = sessionCache.poll();

            if (session != null && session.getValidUntil().isAfter(Instant.now())) {
                sessionDao.create(
                        accountDomain.createSession(
                                session.getToken(),
                                session.getValidUntil(),
                                session.getAccount()
                        )
                );
            }
        }
    }

    private Session updateSession(Session session) {
        sessionCache.remove(session);

        return addSession(session
                .toBuilder()
                .setValidUntil(Instant
                        .now()
                        .plus(TOKEN_LIFETIME_HOURS, ChronoUnit.HOURS))
                .build());
    }

    private Session addSession(Session session) {
        Long accountId = session.getAccount().getId();

        return sessionCache.stream()
                .filter(s -> s.getAccount().getId().equals(accountId))
                .findFirst().orElseGet(() -> {
                    sessionCache.add(session);
                    return session;
                });
    }
}
