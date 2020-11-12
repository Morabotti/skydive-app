package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.ClubProfile;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;

@Singleton
public class ClubProfileDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ClubProfileDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<ClubProfile>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(CLUB_PROFILE.asterisk())
                        .from(CLUB_PROFILE)
                        .where(CLUB_PROFILE.ID.eq(id))
                        .and(CLUB_PROFILE.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(ClubProfile.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(ClubProfile clubProfile) {
        return Transactional.of(
                context -> context.insertInto(CLUB_PROFILE)
                        .set(
                                ClubProfile.mapper.write(
                                        context.newRecord(CLUB_PROFILE),
                                        clubProfile
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(CLUB_PROFILE.CLUB_ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(CLUB_PROFILE)
                            .set(CLUB_PROFILE.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(CLUB_PROFILE.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<ClubProfile>, DSLContext> update(ClubProfile clubProfile) {
        return Transactional.of(
                context -> context.update(CLUB_PROFILE)
                        .set(ClubProfile.mapper.write(
                                context.newRecord(CLUB_PROFILE),
                                clubProfile
                        ))
                        .where(CLUB_PROFILE.ID.eq(clubProfile.getId()))
                        .and(CLUB_PROFILE.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(clubProfile.getId()));
    }
}
