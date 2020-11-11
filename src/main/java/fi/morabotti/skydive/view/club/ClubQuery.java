package fi.morabotti.skydive.view.club;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;

public class ClubQuery {
    @QueryParam("clubId")
    @Nullable
    private Long clubId;

    @QueryParam("accountId")
    @Nullable
    private Long accountId;

    @QueryParam("isPublic")
    @Nullable
    private Boolean isPublic;

    public ClubQuery() {

    }

    public ClubQuery(
            @Nullable Long clubId,
            @Nullable Long accountId,
            @Nullable Boolean isPublic
    ) {
        this.clubId = clubId;
        this.accountId = accountId;
        this.isPublic = isPublic;
    }

    public ClubQuery withClubId(Long id) {
        return new ClubQuery(
                id,
                this.accountId,
                this.isPublic
        );
    }

    public ClubQuery withAccountId(Long id) {
        return new ClubQuery(
                this.clubId,
                id,
                this.isPublic
        );
    }

    public ClubQuery withIsPublic(Boolean isPublic) {
        return new ClubQuery(
                this.clubId,
                this.accountId,
                isPublic
        );
    }

    @Nullable
    public Long getClubId() {
        return this.clubId;
    }

    @Nullable
    public Long getAccountId() {
        return this.accountId;
    }

    @Nullable
    public Boolean getIsPublic() {
        return this.isPublic;
    }
}
