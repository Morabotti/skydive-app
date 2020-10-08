package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.PlaneRecord;

import javax.annotation.Nullable;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.Plane.PLANE;

@EasyValue
@JsonDeserialize(builder = Plane.Builder.class)
public abstract class Plane {
    @EasyId
    public abstract Long getId();

    public abstract String getLicenseNumber();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    @JsonIgnore
    public abstract Club getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Plane.Builder {
    }

    public static final PlaneRecordMapper<PlaneRecord> mapper
            = PlaneRecordMapper.builder(PLANE)
            .setIdAccessor(PLANE.ID)
            .setLicenseNumberAccessor(PLANE.LICENSE_NUMBER)
            .setClubAccessor(PLANE.CLUB_ID, Club::getId)
            .build();
}
