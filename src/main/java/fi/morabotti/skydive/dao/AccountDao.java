package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Profile;
import fi.morabotti.skydive.security.Password;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;
import static fi.morabotti.skydive.db.tables.Profile.PROFILE;

@Singleton
public class AccountDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public AccountDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Optional<Account> findAccountById(Long accountId) {
        return DSL.using(jooqConfiguration)
                .select()
                .from(ACCOUNT)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .where(ACCOUNT.ID.eq(accountId))
                .fetch()
                .stream()
                .collect(Account.mapper.collectingWithProfiles(Profile.mapper));
    }

    public Optional<Account> findAccountByUsername(String username) {
        return DSL.using(jooqConfiguration)
                .select()
                .from(ACCOUNT)
                .where(ACCOUNT.USERNAME.eq(username))
                .fetchOptional()
                .map(Account.mapper::map);
    }

    public Transactional<Optional<Account>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select()
                        .from(ACCOUNT)
                        .where(ACCOUNT.ID.eq(id))
                        .fetchOptional()
                        .map(Account.mapper::map),
                transactionProvider
        );
    }

    public Transactional<Account, DSLContext> create(Account account) {
        return Transactional.of(
                context -> context.insertInto(ACCOUNT)
                        .set(Account.mapper.write(
                                context.newRecord(ACCOUNT),
                                account
                        ))
                        .returning()
                        .fetchOne()
                        .map(Account.mapper::map),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteAccountById(Long id) {
        return Transactional.of(
                context -> {
                    context.update(ACCOUNT)
                            .set(
                                    ACCOUNT.DELETED_AT,
                                    Timestamp.from(Instant.now())
                            ).where(ACCOUNT.ID.eq(id))
                            .and(ACCOUNT.ROLE.notEqual(AccountRole.admin))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> updatePassword(Long id, Password password) {
        return Transactional.of(
                context -> {
                    context
                            .update(ACCOUNT)
                            .set(ACCOUNT.PASSWORD_HASH, password.getHash())
                            .set(ACCOUNT.PASSWORD_SALT, password.getSalt())
                            .where(ACCOUNT.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<Account>, DSLContext> update(Account account) {
        return Transactional.of(
                context -> context.update(ACCOUNT)
                        .set(ACCOUNT.USERNAME, account.getUsername())
                        .set(ACCOUNT.ROLE, account.getAccountRole())
                        .where(ACCOUNT.ID.eq(account.getId()))
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(account.getId()));
    }
}
