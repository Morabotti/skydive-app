package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Session;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
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
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public SessionDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<Session>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> selectAccount(context)
                        .where(SESSION.ID.eq(id))
                        .stream()
                        .map(Session.mapper.withAccount(Account.mapper)::map)
                        .findFirst(),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Session session) {
        return Transactional.of(
                context -> context.insertInto(SESSION)
                        .set(
                                Session.mapper.write(
                                        context.newRecord(SESSION),
                                        session
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(SESSION.ID),
                transactionProvider
        );
    }

    public Optional<Session> findValidByToken(String tokenString) {
        return selectAccount(DSL.using(jooqConfiguration))
                .where(SESSION.TOKEN.eq(tokenString))
                .and(SESSION.VALID_UNTIL.greaterOrEqual(Timestamp.from(Instant.now())))
                .fetch()
                .stream()
                .map(Session.mapper.withAccount(Account.mapper)::map)
                .findFirst();
    }

    public Optional<Session> findValidByAccountId(Long accountId) {
        return selectAccount(DSL.using(jooqConfiguration))
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

    private SelectJoinStep<Record> selectAccount(DSLContext context) {
        return context.select(
                SESSION.asterisk(),
                ACCOUNT.asterisk()
        )
                .from(SESSION)
                .join(ACCOUNT).on(SESSION.ACCOUNT_ID.eq(ACCOUNT.ID));
    }
}
