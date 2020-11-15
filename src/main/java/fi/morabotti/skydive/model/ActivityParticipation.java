package fi.morabotti.skydive.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.ActivityParticipationRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.ActivityParticipation.ACTIVITY_PARTICIPATION;

@EasyValue
@JsonDeserialize(builder = ActivityParticipation.Builder.class)
public abstract class ActivityParticipation {
    @EasyId
    public abstract Long getId();

    public abstract Boolean getActive();

    public abstract Account getAccount();

    public abstract Activity getActivity();

    public abstract Instant getCreatedAt();

    @Nullable
    public abstract Instant getDeletedAt();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ActivityParticipation.Builder {
    }

    public static final ActivityParticipationRecordMapper<ActivityParticipationRecord> mapper
            = ActivityParticipationRecordMapper.builder(ACTIVITY_PARTICIPATION)
            .setIdAccessor(ACTIVITY_PARTICIPATION.ID)
            .setActiveAccessor(ACTIVITY_PARTICIPATION.ACTIVE)
            .setAccountAccessor(ACTIVITY_PARTICIPATION.ACCOUNT_ID, Account::getId)
            .setActivityAccessor(ACTIVITY_PARTICIPATION.ACTIVITY_ID, Activity::getId)
            .setDeletedAtAccessor(
                    ACTIVITY_PARTICIPATION.CREATED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setDeletedAtAccessor(
                    ACTIVITY_PARTICIPATION.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}
