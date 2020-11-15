package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;
import fi.morabotti.skydive.view.club.ClubView;

import java.time.LocalDate;

@EasyValue
@JsonDeserialize(builder = ActivityInformationRequest.Builder.class)
public abstract class ActivityInformationRequest {
    public abstract ClubView getClub();

    public abstract ActivityType getType();

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract ActivityAccess getAccess();

    public abstract Boolean getVisibility();

    public abstract LocalDate getStartDate();

    public abstract LocalDate getEndDate();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ActivityInformationRequest.Builder {
    }
}
