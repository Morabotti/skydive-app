package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubProfile;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.PaginationQuery;
import fi.morabotti.skydive.view.activity.ActivityQuery;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectJoinStep;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static fi.morabotti.skydive.db.tables.Activity.ACTIVITY;
import static fi.morabotti.skydive.db.tables.ActivityParticipation.ACTIVITY_PARTICIPATION;
import static fi.morabotti.skydive.db.tables.Club.CLUB;
import static fi.morabotti.skydive.db.tables.ClubAccount.CLUB_ACCOUNT;
import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;
import static fi.morabotti.skydive.db.tables.PilotActivityParticipation.PILOT_ACTIVITY_PARTICIPATION;

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

    public Long fetchActivitiesLength(
            ActivityQuery activityQuery,
            DateRangeQuery rangeQuery,
            Boolean isPrivate,
            Boolean filterParticipated
    ) {
        return DSL.using(jooqConfiguration)
                .selectCount()
                .from(ACTIVITY)
                .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB)
                .where(getConditions(activityQuery, rangeQuery,isPrivate, filterParticipated))
                .fetchOne(0, Long.class);
    }

    public List<Activity> fetchActivities(
            PaginationQuery paginationQuery,
            DateRangeQuery rangeQuery,
            ActivityQuery activityQuery,
            Boolean isPrivate,
            Boolean filterParticipated
    ) {
        return selectActivity(DSL.using(jooqConfiguration))
                .where(getConditions(activityQuery, rangeQuery, isPrivate, filterParticipated))
                .limit(paginationQuery.getLimit().orElse(20))
                .offset(paginationQuery.getOffset().orElse(0))
                .fetch()
                .stream()
                .collect(Activity.mapper.collectingManyWithClub(
                        Club.mapper.collectingWithClubProfiles(
                                ClubProfile.mapper
                        )
                ));
    }

    public Optional<Activity> getByToken(UUID token) {
        return selectActivity(DSL.using(jooqConfiguration))
                .where(ACTIVITY.TOKEN.eq(token.toString()))
                .and(ACTIVITY.DELETED_AT.isNull())
                .fetch()
                .stream()
                .collect(Activity.mapper.collectingWithClub(
                        Club.mapper.collectingWithClubProfiles(
                                ClubProfile.mapper
                        )
                ));
    }

    public Transactional<Optional<Activity>, DSLContext> getById(Long id) {
        return Transactional.of(
                context -> selectActivity(context)
                        .where(ACTIVITY.ID.eq(id))
                        .and(ACTIVITY.DELETED_AT.isNull())
                        .fetch()
                        .stream()
                        .collect(Activity.mapper.collectingWithClub(
                                Club.mapper.collectingWithClubProfiles(
                                        ClubProfile.mapper
                                )
                        )),
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

    private SelectJoinStep<Record> selectActivity(DSLContext context) {
        return context.select(
                ACTIVITY.asterisk(),
                CLUB.asterisk(),
                CLUB_PROFILE.asterisk()
        )
                .from(ACTIVITY)
                .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB);
    }

    private Condition getConditions(
            ActivityQuery activityQuery,
            DateRangeQuery dateRangeQuery,
            Boolean isPrivate,
            Boolean filterParticipated
    ) {
        Optional<Condition> baseConditions = Optional.of(ACTIVITY.DELETED_AT.isNull())
                .map(condition -> dateRangeQuery.getFrom()
                        .map(from -> condition.and(
                                ACTIVITY.START_DATE.greaterOrEqual(
                                        Timestamp.from(
                                                from.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> dateRangeQuery.getTo()
                        .map(to -> condition.and(
                                ACTIVITY.END_DATE.lessOrEqual(
                                        Timestamp.from(
                                                to.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> activityQuery.getAccess()
                        .map(access -> condition.and(ACTIVITY.ACCESS.eq(access)))
                        .orElse(condition)
                )
                .map(condition -> activityQuery.getType()
                        .map(type -> condition.and(ACTIVITY.TYPE.eq(type)))
                        .orElse(condition)
                )
                .map(condition -> activityQuery.getClubId()
                        .map(id -> condition.and(ACTIVITY.CLUB_ID.eq(id)))
                        .orElse(condition)
                )
                .map(condition -> activityQuery.getAccountId()
                        .map(accountId -> condition.and(ACTIVITY.CLUB_ID.in(
                                DSL.select(CLUB_ACCOUNT.CLUB_ID)
                                        .from(CLUB_ACCOUNT)
                                        .where(CLUB_ACCOUNT.ACCOUNT_ID.eq(accountId))
                        )))
                        .orElse(condition)
                )
                .map(condition -> activityQuery.getSearch()
                        .map(search -> condition.and(ACTIVITY.TITLE.contains(search))
                                .or(CLUB.NAME.contains(search))
                                .or(CLUB.SLUG.contains(search))
                                .or(CLUB_PROFILE.CITY.contains(search))
                        )
                        .orElse(condition)
                );

        if (isPrivate) {
            return baseConditions
                    .map(condition -> activityQuery.getVisible()
                            .map(visible -> condition.and(ACTIVITY.VISIBLE.eq(visible)))
                            .orElse(condition)
                    )
                    .get();
        }

        Condition nonPrivateConditions = baseConditions
                .map(condition -> condition.and(ACTIVITY.VISIBLE.eq(true)))
                .get();

        if (filterParticipated && activityQuery.getAccountId().isPresent()) {
            return nonPrivateConditions.and(
                    ACTIVITY.ID.notIn(
                            DSL.select(ACTIVITY_PARTICIPATION.ACTIVITY_ID)
                                    .from(ACTIVITY_PARTICIPATION)
                                    .where(ACTIVITY_PARTICIPATION.ACCOUNT_ID.eq(
                                            activityQuery.getAccountId().get()
                                    ))
                    )
            ).and(
                    ACTIVITY.ID.notIn(
                            DSL.select(PILOT_ACTIVITY_PARTICIPATION.ACTIVITY_ID)
                                    .from(PILOT_ACTIVITY_PARTICIPATION)
                                    .where(PILOT_ACTIVITY_PARTICIPATION.ACCOUNT_ID.eq(
                                            activityQuery.getAccountId().get()
                                    ))
                    )
            );
        }

        return nonPrivateConditions;
    }
}
