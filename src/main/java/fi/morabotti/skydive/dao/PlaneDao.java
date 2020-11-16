package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Plane;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Plane.PLANE;

@Singleton
public class PlaneDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public PlaneDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public List<Plane> fetchPlanes(Long clubId) {
        return DSL.using(jooqConfiguration)
                .select(PLANE.asterisk())
                .from(PLANE)
                .where(PLANE.CLUB_ID.eq(clubId))
                .and(PLANE.DELETED_AT.isNull())
                .fetch()
                .stream()
                .collect(Plane.mapper);
    }

    public Transactional<Optional<Plane>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(PLANE.asterisk())
                        .from(PLANE)
                        .where(PLANE.ID.eq(id))
                        .and(PLANE.DELETED_AT.isNull())
                        .fetchOptional()
                        .flatMap(Plane.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Long clubId, Plane plane) {
        return Transactional.of(
                context -> context.insertInto(PLANE)
                        .set(
                                Plane.mapper.write(
                                        context.newRecord(PLANE),
                                        plane
                                )
                        )
                        .set(PLANE.CLUB_ID, clubId)
                        .returning()
                        .fetchOne()
                        .get(PLANE.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.update(PLANE)
                            .set(PLANE.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(PLANE.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteByClub(Long clubId) {
        return Transactional.of(
                context -> {
                    context.delete(PLANE)
                            .where(PLANE.CLUB_ID.eq(clubId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<Plane>, DSLContext> update(Long clubId, Plane plane) {
        return Transactional.of(
                context -> context.update(PLANE)
                        .set(Plane.mapper.write(
                                context.newRecord(PLANE),
                                plane
                        ))
                        .set(PLANE.CLUB_ID, clubId)
                        .where(PLANE.ID.eq(plane.getId()))
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(plane.getId()));
    }

}
