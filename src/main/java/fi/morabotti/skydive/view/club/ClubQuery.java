package fi.morabotti.skydive.view.club;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class ClubQuery {
    @QueryParam("city")
    @Nullable
    private String city;

    @QueryParam("isPublic")
    @Nullable
    private Boolean isPublic;

    public ClubQuery() {

    }

    public ClubQuery(
            @Nullable String city,
            @Nullable Boolean isPublic
    ) {
        this.city = city;
        this.isPublic = isPublic;
    }

    public ClubQuery withCity(String city) {
        return new ClubQuery(
                city,
                this.isPublic
        );
    }

    public ClubQuery withIsPublic(Boolean isPublic) {
        return new ClubQuery(
                this.city,
                isPublic
        );
    }

    public Optional<String> getCity() {
        return Optional.ofNullable(this.city);
    }

    public Optional<Boolean> getIsPublic() {
        return Optional.ofNullable(this.isPublic);
    }
}
