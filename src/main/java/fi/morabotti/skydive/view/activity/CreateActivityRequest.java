package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;
import fi.morabotti.skydive.model.Club;

import java.time.LocalDate;

@EasyValue
@JsonDeserialize(builder = CreateActivityRequest.Builder.class)
public abstract class CreateActivityRequest {
    public abstract Club getClub();

    public abstract ActivityType getType();

    public abstract String getDescription();

    public abstract ActivityAccess getAccess();

    public abstract Boolean getVisibility();

    public abstract LocalDate getStartDate();

    public abstract LocalDate getEndDate();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_CreateActivityRequest.Builder {
    }
}
