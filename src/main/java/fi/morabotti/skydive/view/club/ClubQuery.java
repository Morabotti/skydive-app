package fi.morabotti.skydive.view.club;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

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

    public Optional<Long> getClubId() {
        return Optional.ofNullable(this.clubId);
    }

    public Optional<Long> getAccountId() {
        return Optional.ofNullable(this.accountId);
    }

    public Optional<Boolean> getIsPublic() {
        return Optional.ofNullable(this.isPublic);
    }
}
