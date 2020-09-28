package fi.morabotti.skydive.dao;

import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Session;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;
import static fi.morabotti.skydive.db.tables.Session.SESSION;

@Singleton
public class SessionDao {
    private final org.jooq.Configuration jooqConfiguration;

    @Inject
    public SessionDao(Configuration configuration) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
    }

    public Session create(Session session) {
        DSLContext context = DSL.using(jooqConfiguration);

        Long sessionId = context
                .insertInto(SESSION)
                .set(Session.mapper.write(
                        context.newRecord(SESSION),
                        session
                ))
                .returning()
                .fetchOne().component1();

        return Session.mapper
                .withAccount(Account.mapper)
                .map(
                        context.select(
                                SESSION.asterisk(),
                                ACCOUNT.asterisk()
                        )
                                .from(SESSION)
                                .join(ACCOUNT).on(SESSION.ACCOUNT_ID.eq(ACCOUNT.ID))
                                .where(SESSION.ID.eq(sessionId))
                                .fetchOne()
                );
    }

    public Optional<Session> findValidByToken(String tokenString) {
        return fetchWithAccount()
                .where(SESSION.TOKEN.eq(tokenString))
                .and(SESSION.VALID_UNTIL.greaterOrEqual(Timestamp.from(Instant.now())))
                .fetch()
                .stream()
                .map(Session.mapper.withAccount(Account.mapper)::map)
                .findFirst();
    }

    public Optional<Session> findValidByAccountId(Long accountId) {
        return fetchWithAccount()
                .where(SESSION.VALID_UNTIL.greaterOrEqual(Timestamp.from(Instant.now())))
                .and(SESSION.ACCOUNT_ID.eq(accountId))
                .fetch()
                .stream()
                .map(Session.mapper.withAccount(Account.mapper)::map)
                .findFirst();
    }

    public void deleteByToken(String token) {
        DSL.using(jooqConfiguration)
                .delete(SESSION)
                .where(SESSION.TOKEN.eq(token))
                .execute();
    }

    public void deleteExpired() {
        DSL.using(jooqConfiguration)
                .delete(SESSION)
                .where(SESSION.VALID_UNTIL.lessThan(Timestamp.from(Instant.now())))
                .execute();
    }

    private SelectOnConditionStep<Record> fetchWithAccount() {
        return DSL.using(jooqConfiguration)
                .select(
                        SESSION.asterisk(),
                        ACCOUNT.asterisk()
                )
                .from(SESSION)
                .join(ACCOUNT).on(SESSION.ACCOUNT_ID.eq(ACCOUNT.ID));
    }
}
