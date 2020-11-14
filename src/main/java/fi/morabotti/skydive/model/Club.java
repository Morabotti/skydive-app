package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.ClubRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Club.CLUB;

@EasyValue
@JsonDeserialize(builder = Club.Builder.class)
public abstract class Club {
    @EasyId
    public abstract Long getId();

    public abstract String getName();

    public abstract String getSlug();

    public abstract Boolean getIsPublic();

    public abstract List<ClubProfile> getClubProfiles();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Account getCreatorAccount();

    @JsonIgnore
    public Optional<ClubProfile> getClubProfile() {
        return getClubProfiles().stream().findFirst();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Club.Builder {
        public Builder defaults(Builder builder) {
            return builder
                    .setClubProfiles(Collections.emptyList());
        }
    }

    public static final ClubRecordMapper<ClubRecord> mapper
            = ClubRecordMapper.builder(CLUB)
            .setIdAccessor(CLUB.ID)
            .setNameAccessor(CLUB.NAME)
            .setSlugAccessor(CLUB.SLUG)
            .setIsPublicAccessor(CLUB.IS_PUBLIC)
            .setDeletedAtAccessor(
                    CLUB.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setCreatorAccountAccessor(CLUB.CREATOR_ACCOUNT_ID, Account::getId)
            .build();
}
