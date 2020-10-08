package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ClubActivityAccess;
import fi.morabotti.skydive.db.enums.ClubActivityType;
import fi.morabotti.skydive.db.tables.records.ClubActivityRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.ClubActivity.CLUB_ACTIVITY;

@EasyValue
@JsonDeserialize(builder = ClubActivity.Builder.class)
public abstract class ClubActivity {
    @EasyId
    public abstract Long getId();

    public abstract String getDescription();

    public abstract ClubActivityType getType();

    public abstract ClubActivityAccess getAccess();

    public abstract Boolean getVisible();

    public abstract Instant getStartDate();

    @Nullable
    public abstract Instant getEndDate();

    public abstract Instant getCreatedAt();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Club getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubActivity.Builder {
    }

    public static final ClubActivityRecordMapper<ClubActivityRecord> mapper
            = ClubActivityRecordMapper.builder(CLUB_ACTIVITY)
            .setIdAccessor(CLUB_ACTIVITY.ID)
            .setDescriptionAccessor(CLUB_ACTIVITY.DESCRIPTION)
            .setTypeAccessor(CLUB_ACTIVITY.TYPE)
            .setAccessAccessor(CLUB_ACTIVITY.ACCESS)
            .setVisibleAccessor(CLUB_ACTIVITY.VISIBLE)
            .setStartDateAccessor(
                    CLUB_ACTIVITY.START_DATE,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setEndDateAccessor(
                    CLUB_ACTIVITY.END_DATE,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setCreatedAtAccessor(
                    CLUB_ACTIVITY.CREATED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setDeletedAtAccessor(
                    CLUB_ACTIVITY.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setClubAccessor(CLUB_ACTIVITY.CLUB_ID, Club::getId)
            .build();
}
