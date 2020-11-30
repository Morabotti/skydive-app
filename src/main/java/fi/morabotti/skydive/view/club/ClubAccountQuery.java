package fi.morabotti.skydive.view.club;

import fi.morabotti.skydive.db.enums.ClubAccountRole;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class ClubAccountQuery {
    @QueryParam("clubId")
    @Nullable
    private Long clubId;

    @QueryParam("accountId")
    @Nullable
    private Long accountId;

    @QueryParam("role")
    @Nullable
    private ClubAccountRole role;

    @QueryParam("accepted")
    @Nullable
    private Boolean accepted;

    @QueryParam("search")
    @Nullable
    private String search;

    public ClubAccountQuery() {

    }

    public ClubAccountQuery(
            @Nullable Long clubId,
            @Nullable Long accountId,
            @Nullable ClubAccountRole role,
            @Nullable Boolean accepted,
            @Nullable String search
    ) {
        this.clubId = clubId;
        this.accountId = accountId;
        this.role = role;
        this.accepted = accepted;
        this.search = search;
    }

    public ClubAccountQuery withClubId(Long clubId) {
        return new ClubAccountQuery(
                clubId,
                this.accountId,
                this.role,
                this.accepted,
                this.search
        );
    }

    public ClubAccountQuery withAccountId(Long accountId) {
        return new ClubAccountQuery(
                this.clubId,
                accountId,
                this.role,
                this.accepted,
                this.search
        );
    }

    public ClubAccountQuery withRole(ClubAccountRole clubAccountRole) {
        return new ClubAccountQuery(
                this.clubId,
                this.accountId,
                clubAccountRole,
                this.accepted,
                this.search
        );
    }

    public ClubAccountQuery withAccepted(Boolean accepted) {
        return new ClubAccountQuery(
                this.clubId,
                this.accountId,
                this.role,
                accepted,
                this.search
        );
    }

    public ClubAccountQuery withSearch(String search) {
        return new ClubAccountQuery(
                this.clubId,
                this.accountId,
                this.role,
                this.accepted,
                search
        );
    }

    public Optional<Long> getClubId() {
        return Optional.ofNullable(this.clubId);
    }

    public Optional<Long> getAccountId() {
        return Optional.ofNullable(this.accountId);
    }

    public Optional<ClubAccountRole> getRole() {
        return Optional.ofNullable(this.role);
    }

    public Optional<Boolean> getAccepted() {
        return Optional.ofNullable(this.accepted);
    }

    public Optional<String> getSearch() {
        return Optional.ofNullable(this.search);
    }

    public static ClubAccountQuery of(Long clubId, Long accountId) {
        return new ClubAccountQuery()
                .withClubId(clubId)
                .withAccountId(accountId);
    }
}
