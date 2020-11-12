package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Activity;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Activity.ACTIVITY;

@Singleton
public class ActivityDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ActivityDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<Activity>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(ACTIVITY.asterisk())
                        .from(ACTIVITY)
                        .where(ACTIVITY.ID.eq(id))
                        .and(ACTIVITY.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(Activity.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Activity activity) {
        return Transactional.of(
                context -> context.insertInto(ACTIVITY)
                        .set(
                                Activity.mapper.write(
                                        context.newRecord(ACTIVITY),
                                        activity
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(ACTIVITY.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(ACTIVITY)
                            .set(ACTIVITY.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(ACTIVITY.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<Activity>, DSLContext> update(Activity activity) {
        return Transactional.of(
                context -> context.update(ACTIVITY)
                        .set(Activity.mapper.write(
                                context.newRecord(ACTIVITY),
                                activity
                        ))
                        .where(ACTIVITY.ID.eq(activity.getId()))
                        .and(ACTIVITY.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(activity.getId()));
    }
}
