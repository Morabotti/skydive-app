package fi.morabotti.skydive.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;
import fi.morabotti.skydive.db.tables.records.ActivityRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static fi.morabotti.skydive.db.tables.Activity.ACTIVITY;

@EasyValue
@JsonDeserialize(builder = Activity.Builder.class)
public abstract class Activity {
    @EasyId
    public abstract Long getId();

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract ActivityType getType();

    public abstract ActivityAccess getAccess();

    public abstract Boolean getClubOnly();

    public abstract Boolean getVisible();

    public abstract Instant getStartDate();

    public abstract UUID getToken();

    @Nullable
    public abstract Instant getEndDate();

    public abstract Instant getCreatedAt();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    public abstract Club getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Activity.Builder {
    }

    public static final ActivityRecordMapper<ActivityRecord> mapper
            = ActivityRecordMapper.builder(ACTIVITY)
            .setIdAccessor(ACTIVITY.ID)
            .setTitleAccessor(ACTIVITY.TITLE)
            .setDescriptionAccessor(ACTIVITY.DESCRIPTION)
            .setTypeAccessor(ACTIVITY.TYPE)
            .setAccessAccessor(ACTIVITY.ACCESS)
            .setVisibleAccessor(ACTIVITY.VISIBLE)
            .setClubOnlyAccessor(ACTIVITY.CLUB_ONLY)
            .setTokenAccessor(
                    ACTIVITY.TOKEN,
                    UUID::toString,
                    UUID::fromString
            )
            .setStartDateAccessor(
                    ACTIVITY.START_DATE,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setEndDateAccessor(
                    ACTIVITY.END_DATE,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setCreatedAtAccessor(
                    ACTIVITY.CREATED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setDeletedAtAccessor(
                    ACTIVITY.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setClubAccessor(ACTIVITY.CLUB_ID, Club::getId)
            .build();
}
