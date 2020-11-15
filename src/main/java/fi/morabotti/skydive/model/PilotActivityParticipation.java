package fi.morabotti.skydive.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.PilotActivityParticipationRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.PilotActivityParticipation.PILOT_ACTIVITY_PARTICIPATION;

@EasyValue
@JsonDeserialize(builder = PilotActivityParticipation.Builder.class)
public abstract class PilotActivityParticipation {
    @EasyId
    public abstract Long getId();

    public abstract Boolean getActive();

    public abstract Account getAccount();

    public abstract Plane getPlane();

    public abstract Activity getActivity();

    public abstract Instant getCreatedAt();

    @Nullable
    public abstract Instant getDeletedAt();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_PilotActivityParticipation.Builder {
    }

    public static final PilotActivityParticipationRecordMapper<PilotActivityParticipationRecord>
            mapper = PilotActivityParticipationRecordMapper.builder(PILOT_ACTIVITY_PARTICIPATION)
            .setIdAccessor(PILOT_ACTIVITY_PARTICIPATION.ID)
            .setActiveAccessor(PILOT_ACTIVITY_PARTICIPATION.ACTIVE)
            .setAccountAccessor(PILOT_ACTIVITY_PARTICIPATION.ACCOUNT_ID, Account::getId)
            .setActivityAccessor(PILOT_ACTIVITY_PARTICIPATION.ACTIVITY_ID, Activity::getId)
            .setPlaneAccessor(PILOT_ACTIVITY_PARTICIPATION.PLANE_ID, Plane::getId)
            .setDeletedAtAccessor(
                    PILOT_ACTIVITY_PARTICIPATION.CREATED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setDeletedAtAccessor(
                    PILOT_ACTIVITY_PARTICIPATION.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}
