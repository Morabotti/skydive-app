package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Profile;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Profile.PROFILE;

@Singleton
public class ProfileDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ProfileDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<Profile>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(PROFILE.asterisk())
                        .from(PROFILE)
                        .where(PROFILE.ID.eq(id))
                        .and(PROFILE.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(Profile.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Profile profile) {
        return Transactional.of(
                context -> context.insertInto(PROFILE)
                        .set(
                                Profile.mapper.write(
                                        context.newRecord(PROFILE),
                                        profile
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(PROFILE.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(PROFILE)
                            .set(PROFILE.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(PROFILE.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteByAccountId(Long id) {
        return Transactional.of(
                context -> {
                    context.update(PROFILE)
                            .set(PROFILE.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(PROFILE.ACCOUNT_ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<Profile>, DSLContext> update(Profile profile) {
        return Transactional.of(
                context -> context.update(PROFILE)
                        .set(Profile.mapper.write(
                                context.newRecord(PROFILE),
                                profile
                        ))
                        .where(PROFILE.ID.eq(profile.getId()))
                        .and(PROFILE.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(profile.getId()));
    }

}
