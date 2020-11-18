package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.model.ActivityParticipation;
import fi.morabotti.skydive.model.PilotActivityParticipation;
import fi.morabotti.skydive.model.Plane;
import fi.morabotti.skydive.view.AccountView;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@EasyValue
@JsonDeserialize(builder = ActivityParticipationView.Builder.class)
public abstract class ActivityParticipationView {
    public abstract Long getId();

    public abstract Boolean getActive();

    public abstract Instant getCreatedAt();

    public abstract String getRole();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    public abstract AccountView getAccount();

    @Nullable
    public abstract ActivityView getActivity();

    @Nullable
    public abstract Plane getPlane();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ActivityParticipationView.Builder {
    }

    public static ActivityParticipationView of(ActivityParticipation participation) {
        return ActivityParticipationView.builder()
                .setId(participation.getId())
                .setActivity(Optional.ofNullable(participation.getActivity())
                        .map(ActivityView::of)
                        .orElse(null)
                )
                .setAccount(Optional.ofNullable(participation.getAccount())
                        .map(AccountView::of)
                        .orElse(null)
                )
                .setPlane(null)
                .setRole("user")
                .setActive(participation.getActive())
                .setCreatedAt(participation.getCreatedAt())
                .setDeletedAt(participation.getDeletedAt())
                .build();
    }

    public static ActivityParticipationView of(PilotActivityParticipation participation) {
        return ActivityParticipationView.builder()
                .setId(participation.getId())
                .setActivity(Optional.ofNullable(participation.getActivity())
                        .map(ActivityView::of)
                        .orElse(null)
                )
                .setAccount(Optional.ofNullable(participation.getAccount())
                        .map(AccountView::of)
                        .orElse(null)
                )
                .setPlane(participation.getPlane())
                .setRole("pilot")
                .setActive(participation.getActive())
                .setCreatedAt(participation.getCreatedAt())
                .setDeletedAt(participation.getDeletedAt())
                .build();
    }
}
