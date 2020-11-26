package fi.morabotti.skydive.view.activity;

import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class ActivityQuery {
    @QueryParam("type")
    @Nullable
    private ActivityType type;

    @QueryParam("visible")
    @Nullable
    private Boolean visible;

    @QueryParam("access")
    @Nullable
    private ActivityAccess access;

    @QueryParam("clubId")
    @Nullable
    private Long clubId;

    @QueryParam("search")
    @Nullable
    private String search;

    @QueryParam("accountId")
    @Nullable
    private Long accountId;


    public ActivityQuery() {

    }

    public ActivityQuery(
            @Nullable ActivityType type,
            @Nullable Boolean visible,
            @Nullable ActivityAccess access,
            @Nullable Long clubId,
            @Nullable String search,
            @Nullable Long accountId
    ) {
        this.type = type;
        this.visible = visible;
        this.access = access;
        this.clubId = clubId;
        this.search = search;
        this.accountId = accountId;
    }

    public ActivityQuery withType(ActivityType type) {
        return new ActivityQuery(
                type,
                this.visible,
                this.access,
                this.clubId,
                this.search,
                this.accountId
        );
    }

    public ActivityQuery withVisible(Boolean visible) {
        return new ActivityQuery(
                this.type,
                visible,
                this.access,
                this.clubId,
                this.search,
                this.accountId
        );
    }

    public ActivityQuery withAccess(ActivityAccess access) {
        return new ActivityQuery(
                this.type,
                this.visible,
                access,
                this.clubId,
                this.search,
                this.accountId
        );
    }

    public ActivityQuery withClubId(Long clubId) {
        return new ActivityQuery(
                this.type,
                this.visible,
                this.access,
                clubId,
                this.search,
                this.accountId
        );
    }

    public ActivityQuery withSearch(String search) {
        return new ActivityQuery(
                this.type,
                this.visible,
                this.access,
                this.clubId,
                search,
                this.accountId
        );
    }

    public ActivityQuery withAccountId(Long accountId) {
        return new ActivityQuery(
                this.type,
                this.visible,
                this.access,
                this.clubId,
                this.search,
                accountId
        );
    }


    public Optional<ActivityType> getType() {
        return Optional.ofNullable(this.type);
    }

    public Optional<ActivityAccess> getAccess() {
        return Optional.ofNullable(this.access);
    }

    public Optional<Boolean> getVisible() {
        return Optional.ofNullable(this.visible);
    }

    public Optional<Long> getClubId() {
        return Optional.ofNullable(this.clubId);
    }

    public Optional<String> getSearch() {
        return Optional.ofNullable(this.search);
    }

    public Optional<Long> getAccountId() {
        return Optional.ofNullable(this.accountId);
    }
}
