package fi.morabotti.skydive.dao;

import fi.jubic.easyutils.transactional.TransactionProvider;
import fi.jubic.easyutils.transactional.Transactional;
import fi.morabotti.skydive.config.Configuration;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.model.ActivityParticipation;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.PilotActivityParticipation;
import fi.morabotti.skydive.model.Plane;
import fi.morabotti.skydive.model.Profile;
import fi.morabotti.skydive.view.DateRangeQuery;
import fi.morabotti.skydive.view.activity.ActivityParticipationQuery;
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

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;
import static fi.morabotti.skydive.db.tables.Activity.ACTIVITY;
import static fi.morabotti.skydive.db.tables.ActivityParticipation.ACTIVITY_PARTICIPATION;
import static fi.morabotti.skydive.db.tables.Club.CLUB;
import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;
import static fi.morabotti.skydive.db.tables.PilotActivityParticipation.PILOT_ACTIVITY_PARTICIPATION;
import static fi.morabotti.skydive.db.tables.Plane.PLANE;
import static fi.morabotti.skydive.db.tables.Profile.PROFILE;

@Singleton
public class ActivityParticipationDao {
    private final org.jooq.Configuration jooqConfiguration;
    private final TransactionProvider<DSLContext> transactionProvider;

    @Inject
    public ActivityParticipationDao(
            Configuration configuration,
            TransactionProvider<DSLContext> transactionProvider
    ) {
        this.jooqConfiguration = configuration.getJooqConfiguration().getConfiguration();
        this.transactionProvider = transactionProvider;
    }

    public List<PilotActivityParticipation> getPilotParticipants(
            ActivityParticipationQuery participationQuery,
            DateRangeQuery rangeQuery
    ) {
        return selectPilotParticipation(DSL.using(jooqConfiguration))
                .where(getPilotConditions(participationQuery, rangeQuery))
                .and(ACCOUNT.DELETED_AT.isNull())
                .and(PROFILE.DELETED_AT.isNull())
                .and(PLANE.DELETED_AT.isNull())
                .fetch()
                .stream()
                .collect(
                        PilotActivityParticipation.mapper
                                .withPlane(Plane.mapper)
                                .collectingManyWithAccount(
                                        Account.mapper.collectingWithProfiles(
                                                Profile.mapper
                                        )
                                )
                );
    }

    public List<ActivityParticipation> getParticipants(
            ActivityParticipationQuery participationQuery,
            DateRangeQuery rangeQuery
    ) {
        return selectParticipation(DSL.using(jooqConfiguration))
                .where(getConditions(participationQuery, rangeQuery))
                .and(ACCOUNT.DELETED_AT.isNull())
                .and(PROFILE.DELETED_AT.isNull())
                .fetch()
                .stream()
                .collect(
                        ActivityParticipation.mapper
                                .collectingManyWithAccount(
                                        Account.mapper.collectingWithProfiles(
                                                Profile.mapper
                                        )
                                )
                );
    }

    public Transactional<Optional<PilotActivityParticipation>, DSLContext> getPilotParticipation(
            Long id
    ) {
        return Transactional.of(
                context -> selectPilotParticipation(context)
                        .where(PILOT_ACTIVITY_PARTICIPATION.ID.eq(id))
                        .and(PILOT_ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                        .and(ACCOUNT.DELETED_AT.isNull())
                        .and(CLUB.DELETED_AT.isNull())
                        .fetch()
                        .stream()
                        .collect(
                                PilotActivityParticipation.mapper
                                        .withPlane(Plane.mapper)
                                        .withActivity(Activity.mapper.withClub(Club.mapper))
                                        .collectingWithAccount(
                                                Account.mapper.collectingWithProfiles(
                                                        Profile.mapper
                                                )
                                        )
                        ),
                transactionProvider
        );
    }

    public Transactional<Optional<ActivityParticipation>, DSLContext> getParticipation(
            Long id
    ) {
        return Transactional.of(
                context -> selectParticipation(context)
                        .where(ACTIVITY_PARTICIPATION.ID.eq(id))
                        .and(ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                        .and(ACCOUNT.DELETED_AT.isNull())
                        .and(CLUB.DELETED_AT.isNull())
                        .fetch()
                        .stream()
                        .collect(
                                ActivityParticipation.mapper
                                        .withActivity(Activity.mapper.withClub(Club.mapper))
                                        .collectingWithAccount(
                                                Account.mapper.collectingWithProfiles(
                                                        Profile.mapper
                                                )
                                        )
                        ),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(ActivityParticipation participation) {
        return Transactional.of(
                context -> context.insertInto(ACTIVITY_PARTICIPATION)
                        .set(
                                ActivityParticipation.mapper.write(
                                        context.newRecord(ACTIVITY_PARTICIPATION),
                                        participation
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(ACTIVITY_PARTICIPATION.ID),
                transactionProvider
        );
    }

    public Transactional<Long, DSLContext> create(PilotActivityParticipation participation) {
        return Transactional.of(
                context -> context.insertInto(PILOT_ACTIVITY_PARTICIPATION)
                        .set(
                                PilotActivityParticipation.mapper.write(
                                        context.newRecord(PILOT_ACTIVITY_PARTICIPATION),
                                        participation
                                )
                        )
                        .returning()
                        .fetchOne()
                        .get(PILOT_ACTIVITY_PARTICIPATION.ID),
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteActivityParticipation(Long activityId) {
        return Transactional.of(
                context -> {
                    context.update(ACTIVITY_PARTICIPATION)
                            .set(ACTIVITY_PARTICIPATION.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteActivityPilotParticipation(Long activityId) {
        return Transactional.of(
                context -> {
                    context.update(PILOT_ACTIVITY_PARTICIPATION)
                            .set(
                                    PILOT_ACTIVITY_PARTICIPATION.DELETED_AT,
                                    Timestamp.from(Instant.now())
                            )
                            .where(PILOT_ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deleteParticipation(Long id) {
        return Transactional.of(
                context -> {
                    context.update(ACTIVITY_PARTICIPATION)
                            .set(ACTIVITY_PARTICIPATION.DELETED_AT, Timestamp.from(Instant.now()))
                            .where(ACTIVITY_PARTICIPATION.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Void, DSLContext> deletePilotParticipation(Long id) {
        return Transactional.of(
                context -> {
                    context.update(PILOT_ACTIVITY_PARTICIPATION)
                            .set(
                                    PILOT_ACTIVITY_PARTICIPATION.DELETED_AT,
                                    Timestamp.from(Instant.now())
                            )
                            .where(PILOT_ACTIVITY_PARTICIPATION.ID.eq(id))
                            .execute();
                    return null;
                },
                transactionProvider
        );
    }

    public Transactional<Optional<ActivityParticipation>, DSLContext> update(
            ActivityParticipation activityParticipation
    ) {
        return Transactional.of(
                context -> context.update(ACTIVITY_PARTICIPATION)
                        .set(ActivityParticipation.mapper.write(
                                context.newRecord(ACTIVITY_PARTICIPATION),
                                activityParticipation
                        ))
                        .where(ACTIVITY_PARTICIPATION.ID.eq(activityParticipation.getId()))
                        .and(ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getParticipation(activityParticipation.getId()));
    }

    public Transactional<Optional<PilotActivityParticipation>, DSLContext> update(
            PilotActivityParticipation pilotActivityParticipation
    ) {
        return Transactional.of(
                context -> context.update(PILOT_ACTIVITY_PARTICIPATION)
                        .set(PilotActivityParticipation.mapper.write(
                                context.newRecord(PILOT_ACTIVITY_PARTICIPATION),
                                pilotActivityParticipation
                        ))
                        .where(PILOT_ACTIVITY_PARTICIPATION.ID
                                .eq(pilotActivityParticipation.getId()))
                        .and(PILOT_ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                        .execute(),
                transactionProvider
        ).flatMap(ignored -> getPilotParticipation(pilotActivityParticipation.getId()));
    }

    private Condition getPilotConditions(
            ActivityParticipationQuery participationQuery,
            DateRangeQuery rangeQuery
    ) {
        return Optional.of(PILOT_ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                .map(condition -> rangeQuery.getFrom()
                        .map(from -> condition.and(
                                PILOT_ACTIVITY_PARTICIPATION.CREATED_AT.greaterOrEqual(
                                        Timestamp.from(
                                                from.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> rangeQuery.getTo()
                        .map(to -> condition.and(
                                PILOT_ACTIVITY_PARTICIPATION.CREATED_AT.lessOrEqual(
                                        Timestamp.from(
                                                to.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> participationQuery.getAccountId()
                        .map(accountId -> condition.and(
                                PILOT_ACTIVITY_PARTICIPATION.ACCOUNT_ID.eq(accountId))
                        )
                        .orElse(condition)
                )
                .map(condition -> participationQuery.getActivityId()
                        .map(activityId -> condition.and(
                                PILOT_ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                        )
                        .orElse(condition)
                )
                .map(condition -> participationQuery.getActive()
                        .map(active -> condition.and(
                                PILOT_ACTIVITY_PARTICIPATION.ACTIVE.eq(active))
                        )
                        .orElse(condition)
                )
                .get();
    }

    private Condition getConditions(
            ActivityParticipationQuery participationQuery,
            DateRangeQuery rangeQuery
    ) {
        return Optional.of(ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                .map(condition -> rangeQuery.getFrom()
                        .map(from -> condition.and(
                                ACTIVITY_PARTICIPATION.CREATED_AT.greaterOrEqual(
                                        Timestamp.from(
                                                from.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> rangeQuery.getTo()
                        .map(to -> condition.and(
                                ACTIVITY_PARTICIPATION.CREATED_AT.lessOrEqual(
                                        Timestamp.from(
                                                to.atStartOfDay().toInstant(ZoneOffset.UTC)
                                        )
                                )
                        )).orElse(condition)
                )
                .map(condition -> participationQuery.getAccountId()
                        .map(accountId -> condition.and(
                                ACTIVITY_PARTICIPATION.ACCOUNT_ID.eq(accountId))
                        )
                        .orElse(condition)
                )
                .map(condition -> participationQuery.getActivityId()
                        .map(activityId -> condition.and(
                                ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                        )
                        .orElse(condition)
                )
                .map(condition -> participationQuery.getActive()
                        .map(active -> condition.and(ACTIVITY_PARTICIPATION.ACTIVE.eq(active)))
                        .orElse(condition)
                )
                .get();
    }

    private SelectJoinStep<Record> selectParticipation(DSLContext context) {
        return context.select(
                ACTIVITY_PARTICIPATION.asterisk(),
                ACCOUNT.asterisk(),
                PROFILE.asterisk(),
                ACTIVITY.asterisk(),
                CLUB.asterisk(),
                CLUB_PROFILE.asterisk()
        )
                .from(ACTIVITY_PARTICIPATION)
                .join(ACCOUNT).onKey(Keys.FK_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .join(ACTIVITY).onKey(Keys.FK_ACTIVITY_PARTICIPATION_ACTIVITY_ID)
                .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB);
    }

    private SelectJoinStep<Record> selectPilotParticipation(DSLContext context) {
        return context.select(
                PILOT_ACTIVITY_PARTICIPATION.asterisk(),
                ACTIVITY.asterisk(),
                ACCOUNT.asterisk(),
                PROFILE.asterisk(),
                CLUB.asterisk(),
                CLUB_PROFILE.asterisk(),
                PLANE.asterisk()
        )
                .from(PILOT_ACTIVITY_PARTICIPATION)
                .join(ACTIVITY).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_ACTIVITY_ID)
                .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                .join(ACCOUNT).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .join(PLANE).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID)
                .join(CLUB_PROFILE).onKey(Keys.FK_CLUB_PROFILE_CLUB);
    }
}
