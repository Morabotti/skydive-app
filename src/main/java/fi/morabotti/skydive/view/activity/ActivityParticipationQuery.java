package fi.morabotti.skydive.view.activity;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class ActivityParticipationQuery {
    @QueryParam("accountId")
    @Nullable
    private Long accountId;

    @QueryParam("activityId")
    @Nullable
    private Long activityId;

    @QueryParam("active")
    @Nullable
    private Boolean active;

    public ActivityParticipationQuery() {

    }

    public ActivityParticipationQuery(
            @Nullable Long accountId,
            @Nullable Long activityId,
            @Nullable Boolean active
    ) {
        this.accountId = accountId;
        this.activityId = activityId;
        this.active = active;
    }

    public ActivityParticipationQuery withAccountId(Long accountId) {
        return new ActivityParticipationQuery(
                accountId,
                this.activityId,
                this.active
        );
    }

    public ActivityParticipationQuery withActivityId(Long activityId) {
        return new ActivityParticipationQuery(
                this.accountId,
                activityId,
                this.active
        );
    }

    public ActivityParticipationQuery withActive(Boolean active) {
        return new ActivityParticipationQuery(
                this.accountId,
                this.activityId,
                active
        );
    }

    public Optional<Long> getAccountId() {
        return Optional.ofNullable(this.accountId);
    }

    public Optional<Long> getActivityId() {
        return Optional.ofNullable(this.activityId);
    }

    public Optional<Boolean> getActive() {
        return Optional.ofNullable(this.active);
    }
}
