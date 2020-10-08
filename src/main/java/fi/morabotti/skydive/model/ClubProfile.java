package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.ClubProfileRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.ClubProfile.CLUB_PROFILE;

@EasyValue
@JsonDeserialize(builder = ClubProfile.Builder.class)
public abstract class ClubProfile {
    @EasyId
    public abstract Long getId();

    @Nullable
    public abstract String getDescription();

    @Nullable
    public abstract String getAddress();

    @Nullable
    public abstract String getZipCode();

    @Nullable
    public abstract String getCity();

    @Nullable
    public abstract String getPhone();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Club getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubProfile.Builder {
    }

    public static final ClubProfileRecordMapper<ClubProfileRecord> mapper
            = ClubProfileRecordMapper.builder(CLUB_PROFILE)
            .setIdAccessor(CLUB_PROFILE.ID)
            .setDescriptionAccessor(CLUB_PROFILE.DESCRIPTION)
            .setAddressAccessor(CLUB_PROFILE.ADDRESS)
            .setZipCodeAccessor(CLUB_PROFILE.ZIPCODE)
            .setCityAccessor(CLUB_PROFILE.CITY)
            .setPhoneAccessor(CLUB_PROFILE.PHONE)
            .setDeletedAtAccessor(
                    CLUB_PROFILE.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setClubAccessor(CLUB_PROFILE.CLUB_ID, Club::getId)
            .build();
}
