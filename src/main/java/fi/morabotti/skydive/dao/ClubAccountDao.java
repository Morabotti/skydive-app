package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.model.ClubAccount;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.ClubAccount.CLUB_ACCOUNT;

@Singleton
public class ClubAccountDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ClubAccountDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Boolean checkIfMember(Long clubId, Long accountId) {
        return DSL.using(jooqConfiguration)
                .select()
                .from(CLUB_ACCOUNT)
                .where(CLUB_ACCOUNT.CLUB_ID.eq(clubId))
                .and(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId))
                .fetchOptional()
                .isPresent();
    }

    public Transactional<Optional<ClubAccount>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(CLUB_ACCOUNT.asterisk())
                        .from(CLUB_ACCOUNT)
                        .where(CLUB_ACCOUNT.ID.eq(id))
                        .fetchOptional()
                        .map(ClubAccount.mapper::map),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(
            Long clubId,
            Long accountId,
            ClubAccountRole role
    ) {
        return Transactional.of(
                context -> context.insertInto(CLUB_ACCOUNT)
                        .set(CLUB_ACCOUNT.ACCOUNT_ID, accountId)
                        .set(CLUB_ACCOUNT.CLUB_ID, clubId)
                        .set(CLUB_ACCOUNT.ROLE, role)
                        .returning()
                        .fetchOne()
                        .get(CLUB_ACCOUNT.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.delete(CLUB_ACCOUNT)
                            .where(CLUB_ACCOUNT.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }
}
