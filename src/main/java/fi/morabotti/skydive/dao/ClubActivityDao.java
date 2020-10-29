package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.ClubActivity;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.ClubActivity.CLUB_ACTIVITY;

@Singleton
public class ClubActivityDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ClubActivityDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<ClubActivity>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(CLUB_ACTIVITY.asterisk())
                        .from(CLUB_ACTIVITY)
                        .where(CLUB_ACTIVITY.ID.eq(id))
                        .and(CLUB_ACTIVITY.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(ClubActivity.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(ClubActivity clubActivity) {
        return Transactional.of(
                context -> context.insertInto(CLUB_ACTIVITY)
                        .set(
                                ClubActivity.mapper.write(
                                        context.newRecord(CLUB_ACTIVITY),
                                        clubActivity
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(CLUB_ACTIVITY.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(CLUB_ACTIVITY)
                            .set(CLUB_ACTIVITY.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(CLUB_ACTIVITY.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<ClubActivity>, DSLContext> update(ClubActivity clubActivity) {
        return Transactional.of(
                context -> context.update(CLUB_ACTIVITY)
                        .set(ClubActivity.mapper.write(
                                context.newRecord(CLUB_ACTIVITY),
                                clubActivity
                        ))
                        .where(CLUB_ACTIVITY.ID.eq(clubActivity.getId()))
                        .and(CLUB_ACTIVITY.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(clubActivity.getId()));
    }
}
