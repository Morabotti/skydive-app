package fi.morabotti.skydive.view.club;

import javax.annotation.Nullable;
import javax.ws.rs.QueryParam;
import java.util.Optional;

public class ClubQuery {
    @QueryParam("city")
    @Nullable
    private String city;

    @QueryParam("search")
    @Nullable
    private String search;

    @QueryParam("isPublic")
    @Nullable
    private Boolean isPublic;

    public ClubQuery() {

    }

    public ClubQuery(
            @Nullable String city,
            @Nullable Boolean isPublic,
            @Nullable String search
    ) {
        this.city = city;
        this.isPublic = isPublic;
        this.search = search;
    }

    public ClubQuery withCity(String city) {
        return new ClubQuery(
                city,
                this.isPublic,
                this.search
        );
    }

    public ClubQuery withIsPublic(Boolean isPublic) {
        return new ClubQuery(
                this.city,
                isPublic,
                this.search
        );
    }

    public ClubQuery withSearch(String search) {
        return new ClubQuery(
                this.city,
                this.isPublic,
                search
        );
    }

    public Optional<String> getCity() {
        return Optional.ofNullable(this.city);
    }

    public Optional<Boolean> getIsPublic() {
        return Optional.ofNullable(this.isPublic);
    }

    public Optional<String> getSearch() {
        return Optional.ofNullable(this.search);
    }
}
