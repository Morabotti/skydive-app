package fi.morabotti.skydive.view.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;
import fi.morabotti.skydive.model.Activity;
import fi.morabotti.skydive.view.club.ClubView;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@EasyValue
@JsonDeserialize(builder = ActivityView.Builder.class)
public abstract class ActivityView {
    public abstract Long getId();

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract ActivityType getType();

    public abstract ActivityAccess getAccess();

    public abstract Boolean getVisible();

    public abstract UUID getToken();

    public abstract Instant getStartDate();

    @Nullable
    public abstract Instant getEndDate();

    public abstract Instant getCreatedAt();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    public abstract ClubView getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ActivityView.Builder {
    }

    public static ActivityView of(Activity activity) {
        return ActivityView.builder()
                .setId(activity.getId())
                .setTitle(activity.getTitle())
                .setDescription(activity.getDescription())
                .setType(activity.getType())
                .setAccess(activity.getAccess())
                .setVisible(activity.getVisible())
                .setToken(activity.getToken())
                .setClub(Optional.ofNullable(activity.getClub())
                        .map(ClubView::of)
                        .orElse(null)
                )
                .setStartDate(activity.getStartDate())
                .setEndDate(activity.getEndDate())
                .setCreatedAt(activity.getCreatedAt())
                .setDeletedAt(activity.getDeletedAt())
                .build();
    }
}
