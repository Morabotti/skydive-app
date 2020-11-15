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
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;
import static fi.morabotti.skydive.db.tables.Activity.ACTIVITY;
import static fi.morabotti.skydive.db.tables.ActivityParticipation.ACTIVITY_PARTICIPATION;
import static fi.morabotti.skydive.db.tables.Club.CLUB;
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
            Long activityId
    ) {
        return DSL.using(jooqConfiguration)
                .select(
                        PILOT_ACTIVITY_PARTICIPATION.asterisk(),
                        ACCOUNT.asterisk(),
                        PROFILE.asterisk(),
                        PLANE.asterisk()
                )
                .from(PILOT_ACTIVITY_PARTICIPATION)
                .join(ACCOUNT).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .join(PLANE).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID)
                .where(PILOT_ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                .and(PILOT_ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
                .and(ACCOUNT.DELETED_AT.isNull())
                .and(PROFILE.DELETED_AT.isNull())
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
            Long activityId
    ) {
        return DSL.using(jooqConfiguration)
                .select(
                        ACTIVITY_PARTICIPATION.asterisk(),
                        ACCOUNT.asterisk(),
                        PROFILE.asterisk()
                )
                .from(ACTIVITY_PARTICIPATION)
                .join(ACCOUNT).onKey(Keys.FK_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                .where(ACTIVITY_PARTICIPATION.ACTIVITY_ID.eq(activityId))
                .and(ACTIVITY_PARTICIPATION.DELETED_AT.isNull())
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
                context -> context
                        .select(
                                PILOT_ACTIVITY_PARTICIPATION.asterisk(),
                                ACTIVITY.asterisk(),
                                ACCOUNT.asterisk(),
                                PROFILE.asterisk(),
                                CLUB.asterisk(),
                                PLANE.asterisk()
                        )
                        .from(PILOT_ACTIVITY_PARTICIPATION)
                        .join(ACTIVITY).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_ACTIVITY_ID)
                        .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                        .join(ACCOUNT).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                        .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
                        .join(PLANE).onKey(Keys.FK_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID)
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
                context -> context
                        .select(
                                ACTIVITY_PARTICIPATION.asterisk(),
                                ACTIVITY.asterisk(),
                                ACCOUNT.asterisk(),
                                PROFILE.asterisk(),
                                CLUB.asterisk()
                        )
                        .from(ACTIVITY_PARTICIPATION)
                        .join(ACTIVITY).onKey(Keys.FK_ACTIVITY_PARTICIPATION_ACTIVITY_ID)
                        .join(CLUB).onKey(Keys.FK_ACTIVITY_CLUB_ID)
                        .join(ACCOUNT).onKey(Keys.FK_ACTIVITY_PARTICIPATION_ACCOUNT_ID)
                        .join(PROFILE).onKey(Keys.FK_PROFILE_ACCOUNT)
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

    public Transactional<Optional<ActivityParticipation>, DSLContext> updateParticipation(
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

    public Transactional<Optional<PilotActivityParticipation>, DSLContext> updatePilotParticipation(
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
}
