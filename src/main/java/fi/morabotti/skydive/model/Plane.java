package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.PlaneRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.Plane.PLANE;

@EasyValue
@JsonDeserialize(builder = Plane.Builder.class)
public abstract class Plane {
    @EasyId
    public abstract Long getId();

    public abstract String getLicenseNumber();

    public abstract Boolean getActive();

    public abstract Integer getSeats();

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
            .setSeatsAccessor(PLANE.SEATS)
            .setActiveAccessor(PLANE.ACTIVE)
            .setClubAccessor(PLANE.CLUB_ID, Club::getId)
            .setDeletedAtAccessor(
                    PLANE.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}
