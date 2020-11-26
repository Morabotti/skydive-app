package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;

import java.util.List;

@EasyValue
@JsonDeserialize(builder = PersonalActivityView.Builder.class)
public abstract class PersonalActivityView {
    public abstract List<ActivityView> getActivities();

    public abstract List<ActivityParticipationView> getParticipation();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_PersonalActivityView.Builder {
    }

    public static PersonalActivityView of(
            List<ActivityView> activityViews,
            List<ActivityParticipationView> participationViews
    ) {
        return PersonalActivityView.builder()
                .setActivities(activityViews)
                .setParticipation(participationViews)
                .build();
    }
}
