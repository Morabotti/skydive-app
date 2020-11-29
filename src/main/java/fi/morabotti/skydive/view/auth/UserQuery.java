package fi.morabotti.skydive.view.auth;

import fi.morabotti.skydive.db.enums.AccountRole;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class UserQuery {
    @QueryParam("location")
    @Nullable
    private String location;

    @QueryParam("search")
    @Nullable
    private String search;

    @QueryParam("role")
    @Nullable
    private AccountRole role;

    @QueryParam("showDeleted")
    @Nullable
    private Boolean showDeleted;

    public UserQuery() {

    }

    public UserQuery(
            @Nullable String location,
            @Nullable String search,
            @Nullable AccountRole role,
            @Nullable Boolean showDeleted
    ) {
        this.location = location;
        this.search = search;
        this.role = role;
        this.showDeleted = showDeleted;
    }

    public UserQuery withLocation(String location) {
        return new UserQuery(
                location,
                this.search,
                this.role,
                this.showDeleted
        );
    }

    public UserQuery withSearch(String search) {
        return new UserQuery(
                this.location,
                search,
                this.role,
                this.showDeleted
        );
    }

    public UserQuery withRole(AccountRole role) {
        return new UserQuery(
                this.location,
                this.search,
                role,
                this.showDeleted
        );
    }

    public UserQuery withShowDeleted(Boolean showDeleted) {
        return new UserQuery(
                this.location,
                this.search,
                this.role,
                showDeleted
        );
    }


    public Optional<String> getLocation() {
        return Optional.ofNullable(this.location);
    }

    public Optional<String> getSearch() {
        return Optional.ofNullable(this.search);
    }

    public Optional<AccountRole> getRole() {
        return Optional.ofNullable(this.role);
    }

    public Optional<Boolean> getShowDeleted() {
        return Optional.ofNullable(this.showDeleted);
    }
}
