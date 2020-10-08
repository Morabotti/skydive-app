package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.ProfileRecord;

import javax.annotation.Nullable;

import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.Profile.PROFILE;

@EasyValue
@JsonDeserialize(builder = Profile.Builder.class)
public abstract class Profile {
    @EasyId
    public abstract Long getId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getAddress();

    public abstract String getZipCode();

    public abstract String getCity();

    public abstract String getPhone();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Account getAccount();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Profile.Builder {
    }

    public static final ProfileRecordMapper<ProfileRecord> mapper
            = ProfileRecordMapper.builder(PROFILE)
            .setIdAccessor(PROFILE.ID)
            .setFirstNameAccessor(PROFILE.FIRST_NAME)
            .setLastNameAccessor(PROFILE.LAST_NAME)
            .setAddressAccessor(PROFILE.ADDRESS)
            .setZipCodeAccessor(PROFILE.ZIPCODE)
            .setCityAccessor(PROFILE.CITY)
            .setPhoneAccessor(PROFILE.PHONE)
            .setDeletedAtAccessor(
                    PROFILE.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .setAccountAccessor(PROFILE.ACCOUNT_ID, Account::getId)
            .build();
}
