package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.model.Plane;
import org.jooq.DSLContext;

import javax.inject.Inject;
import javax.inject.Singleton;
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

    public Transactional<Optional<Plane>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> context
                        .select(PLANE.asterisk())
                        .from(PLANE)
                        .where(PLANE.ID.eq(id))
                        .fetchOptional()
                        .flatMap(Plane.mapper::mapOptional),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(Plane club) {
        return Transactional.of(
                context -> context.insertInto(PLANE)
                        .set(
                                Plane.mapper.write(
                                        context.newRecord(PLANE),
                                        club
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(PLANE.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> delete(Long id) {
        return Transactional.of(
                context -> {
                    context.delete(PLANE)
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

    public Transactional<Optional<Plane>, DSLContext> update(Plane plane) {
        return Transactional.of(
                context -> context.update(PLANE)
                        .set(Plane.mapper.write(
                                context.newRecord(PLANE),
                                plane
                        ))
                        .where(PLANE.ID.eq(plane.getId()))
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getById(plane.getId()));
    }

}
