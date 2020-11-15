package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.model.ActivityParticipation;
import fi.morabotti.skydive.model.PilotActivityParticipation;

import java.util.List;

@EasyValue
@JsonDeserialize(builder = ActivityParticipationView.Builder.class)
public abstract class ActivityParticipationView {
    public abstract List<PilotActivityParticipation> getPilotParticipants();

    public abstract List<ActivityParticipation> getParticipants();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ActivityParticipationView.Builder {
    }

    public static ActivityParticipationView create(
            List<ActivityParticipation> participants,
            List<PilotActivityParticipation> pilotParticipants
    ) {
        return ActivityParticipationView.builder()
                .setParticipants(participants)
                .setPilotParticipants(pilotParticipants)
                .build();
    }
}
