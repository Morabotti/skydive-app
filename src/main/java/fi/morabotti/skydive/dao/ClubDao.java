package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.club.ClubQuery;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Club.CLUB;
import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;

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

    public Long fetchClubLength(ClubQuery clubQuery, Boolean isPrivate) {
        return DSL.using(jooqConfiguration)
                .selectCount()
                .from(CLUB)
                .where(getConditions(clubQuery, isPrivate))
                .fetchOne(0, Long.class);
    }

    public List<Club> fetchClubs(
            PaginationQuery paginationQuery,
            ClubQuery clubQuery,
            Boolean isPrivate
    ) {
        return selectClub(DSL.using(jooqConfiguration))
                .where(getConditions(clubQuery, isPrivate))
                .limit(paginationQuery.getLimit().orElse(20))
                .offset(paginationQuery.getOffset().orElse(0))
                .fetch()
                .stream()
                .collect(Club.mapper.collectingManyWithClubProfiles(ClubProfile.mapper));
    }

    public Optional<Club> checkClubBySlug(String clubSlug) {
        return DSL.using(jooqConfiguration)
                .select()
                .from(CLUB)
                .where(CLUB.SLUG.eq(clubSlug))
                .and(CLUB.DELETED_AT.isNull())
                .fetchOptional()
                .map(Club.mapper::map);
    }

    public Transactional<Optional<Club>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> selectClub(context)
                        .where(CLUB.ID.eq(id))
                        .and(CLUB.DELETED_AT.isNull())
                        .fetch()
                        .stream()
                        .collect(Club.mapper.collectingWithClubProfiles(ClubProfile.mapper)),
                transactionProvider
        );
    }

    public Transactional<Optional<Club>, DSLContext> getClubBySlug(String slug) {
        return Transactional.of(
                context -> selectClub(context)
                        .where(CLUB.SLUG.eq(slug))
                        .and(CLUB.DELETED_AT.isNull())
                        .fetch()
                        .stream()
                        .collect(Club.mapper.collectingWithClubProfiles(ClubProfile.mapper)),
                transactionProvider
        );
    }

    public Transactional<Club, DSLContext> create(Club club) {
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
                        .map(Club.mapper::map),
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


    private Condition getConditions(
            ClubQuery clubQuery,
            Boolean isPrivate
    ) {
        if (isPrivate) {
            return Optional.of(CLUB.DELETED_AT.isNull())
                    .map(condition -> clubQuery.getCity()
                            .map(city -> condition.and(CLUB_PROFILE.CITY.eq(city)))
                            .orElse(condition)
                    )
                    .map(condition -> clubQuery.getIsPublic()
                            .map(isPublic -> condition.and(CLUB.IS_PUBLIC.eq(isPublic)))
                            .orElse(condition)
                    )
                    .get();
        }

        return CLUB.DELETED_AT.isNull().and(CLUB.IS_PUBLIC.eq(true));
    }

    private SelectJoinStep<Record> selectClub(DSLContext context) {
        return context.select(
                        CLUB.asterisk(),
                        CLUB_PROFILE.asterisk()
                )
                .from(CLUB)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB);
    }
}
