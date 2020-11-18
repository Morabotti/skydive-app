package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.model.Profile;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.club.ClubAccountQuery;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;
import static fi.morabotti.skydive.db.tables.Club.CLUB;
import static fi.morabotti.skydive.db.tables.ClubAccount.CLUB_ACCOUNT;
import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;
import static fi.morabotti.skydive.db.tables.Profile.PROFILE;

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

    public Long fetchClubMembersLength(ClubAccountQuery clubQuery) {
        return DSL.using(jooqConfiguration)
                .selectCount()
                .from(CLUB_ACCOUNT)
                .where(getConditions(clubQuery))
                .fetchOne(0, Long.class);
    }

    public List<ClubAccount> fetchClubs(
            PaginationQuery paginationQuery,
            ClubAccountQuery clubAccountQuery
    ) {
        return selectClubAccount(DSL.using(jooqConfiguration))
                .where(getConditions(clubAccountQuery))
                .limit(paginationQuery.getLimit().orElse(20))
                .offset(paginationQuery.getOffset().orElse(0))
                .fetch()
                .stream()
                .collect(ClubAccount.mapper
                        .collectingManyWithClub(
                                Club.mapper.collectingWithClubProfiles(
                                        ClubProfile.mapper
                                )
                        )
                );
    }

    public List<ClubAccount> fetchClubAccounts(
            PaginationQuery paginationQuery,
            ClubAccountQuery clubAccountQuery
    ) {
        return selectClubAccount(DSL.using(jooqConfiguration))
                .where(getConditions(clubAccountQuery))
                .limit(paginationQuery.getLimit().orElse(20))
                .offset(paginationQuery.getOffset().orElse(0))
                .fetch()
                .stream()
                .collect(ClubAccount.mapper
                        .collectingManyWithAccount(
                                Account.mapper.collectingWithProfiles(
                                        Profile.mapper
                                )
                        )
                );
    }

    public Transactional<Optional<ClubAccount>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> selectClubAccount(context)
                        .where(CLUB_ACCOUNT.ID.eq(id))
                        .fetch()
                        .stream()
                        .collect(ClubAccount.mapper
                                .withClub(Club.mapper)
                                .collectingWithAccount(
                                        Account.mapper.collectingWithProfiles(
                                                Profile.mapper
                                        )
                                )
                        ),
                transactionProvider
        );
    }

    public Transactional<Optional<ClubAccount>, DSLContext> findAccount(
            ClubAccountQuery clubAccountQuery
    ) {
        return Transactional.of(
                context -> selectClubAccount(context)
                        .where(getConditions(clubAccountQuery))
                        .fetch()
                        .stream()
                        .collect(ClubAccount.mapper
                                .withClub(Club.mapper)
                                .collectingWithAccount(
                                        Account.mapper.collectingWithProfiles(
                                                Profile.mapper
                                        )
                                )
                        ),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(
            Long clubId,
            Long accountId,
            ClubAccountRole role,
            Boolean accepted
    ) {
        return Transactional.of(
                context -> context.insertInto(CLUB_ACCOUNT)
                        .set(CLUB_ACCOUNT.ACCOUNT_ID, accountId)
                        .set(CLUB_ACCOUNT.CLUB_ID, clubId)
                        .set(CLUB_ACCOUNT.ROLE, role)
                        .set(CLUB_ACCOUNT.ACCEPTED, accepted)
                        .returning()
                        .fetchOne()
                        .get(CLUB_ACCOUNT.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> updateStatus(
            Long clubId,
            Long accountId,
            Boolean accepted
    ) {
        return Transactional.of(
                context -> {
                    context.update(CLUB_ACCOUNT)
                            .set(CLUB_ACCOUNT.ACCEPTED, accepted)
                            .where(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId))
                            .and(CLUB_ACCOUNT.CLUB_ID.eq(clubId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteByAccountId(Long accountId) {
        return Transactional.of(
                context -> {
                    context.delete(CLUB_ACCOUNT)
                            .where(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteByClubId(Long clubId) {
        return Transactional.of(
                context -> {
                    context.delete(CLUB_ACCOUNT)
                            .where(CLUB_ACCOUNT.CLUB_ID.eq(clubId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long clubId, Long accountId) {
        return Transactional.of(
                context -> {
                    context.delete(CLUB_ACCOUNT)
                            .where(CLUB_ACCOUNT.CLUB_ID.eq(clubId))
                            .and(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId))
                            .execute();
                    return null;
                },
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

    private SelectJoinStep<Record> selectClubAccount(DSLContext context) {
        return context.select(
                CLUB_ACCOUNT.asterisk(),
                ACCOUNT.asterisk(),
                PROFILE.asterisk(),
                CLUB.asterisk(),
                CLUB_PROFILE.asterisk()
        )
                .from(CLUB_ACCOUNT)
                .join(ACCOUNT).onKey(Keys.FK_CLUB_ACCOUNT_ACCOUNT_ID)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .join(CLUB).onKey(Keys.FK_CLUB_ACCOUNT_CLUB_ID)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB);
    }

    private Condition getConditions(ClubAccountQuery accountQuery) {
        return Optional.of(CLUB_ACCOUNT.CREATED_AT.isNotNull())
                .map(condition -> accountQuery.getClubId()
                        .map(clubId -> condition.and(CLUB_ACCOUNT.CLUB_ID.eq(clubId)))
                        .orElse(condition)
                )
                .map(condition -> accountQuery.getAccountId()
                        .map(accountId -> condition.and(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId)))
                        .orElse(condition)
                )
                .map(condition -> accountQuery.getAccepted()
                        .map(accepted -> condition.and(CLUB_ACCOUNT.ACCEPTED.eq(accepted)))
                        .orElse(condition)
                )
                .map(condition -> accountQuery.getRole()
                        .map(role -> condition.and(CLUB_ACCOUNT.ROLE.eq(role)))
                        .orElse(condition)
                )
                .get();
    }
}
