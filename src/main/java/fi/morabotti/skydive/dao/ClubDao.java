package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Club;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Club.CLUB;

@Singleton
public class ClubDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ClubDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public Transactional<Optional<Club>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(CLUB.asterisk())
                        .from(CLUB)
                        .where(CLUB.ID.eq(id))
                        .and(CLUB.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(Club.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Club club) {
        return Transactional.of(
                context -> context.insertInto(CLUB)
                        .set(
                                Club.mapper.write(
                                        context.newRecord(CLUB),
                                        club
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(CLUB.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(CLUB)
                            .set(CLUB.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(CLUB.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<Club>, DSLContext> update(Club club) {
        return Transactional.of(
                context -> context.update(CLUB)
                        .set(Club.mapper.write(
                                context.newRecord(CLUB),
                                club
                        ))
                        .where(CLUB.ID.eq(club.getId()))
                        .and(CLUB.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(club.getId()));
    }

}
