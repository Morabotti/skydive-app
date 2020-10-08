package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.ClubRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.Club.CLUB;

@EasyValue
@JsonDeserialize(builder = Club.Builder.class)
public abstract class Club {
    @EasyId
    public abstract Long getId();

    public abstract String getName();

    public abstract String getSlug();

    public abstract Boolean getIsPublic();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Account getCreatorAccount();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Club.Builder {
    }

    public static final ClubRecordMapper<ClubRecord> mapper
            = ClubRecordMapper.builder(CLUB)
            .setIdAccessor(CLUB.ID)
            .setNameAccessor(CLUB.NAME)
            .setSlugAccessor(CLUB.SLUG)
            .setIsPublicAccessor(CLUB.PUBLIC)
            .setDeletedAtAccessor(
                    CLUB.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setCreatorAccountAccessor(CLUB.CREATOR_ACCOUNT_ID, Account::getId)
            .build();
}
