package fi.morabotti.skydive.view.club;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.model.Club;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.model.ClubProfile;

import javax.annotation.Nullable;
import javax.ws.rs.InternalServerErrorException;
import java.time.Instant;
import java.util.Optional;

@EasyValue
@JsonDeserialize(builder = ClubView.Builder.class)
public abstract class ClubView {
    public abstract Long getId();

    public abstract String getName();

    public abstract String getSlug();

    public abstract Boolean getIsPublic();

    @Nullable
    public abstract ClubProfile getClubProfile();

    @Nullable
    public abstract Instant getDeletedAt();

    @Nullable
    public abstract ClubAccountView getClubAccount();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubView.Builder {

    }

    public static ClubView of(Club club) {
        return ClubView.builder()
                .setId(club.getId())
                .setName(club.getName())
                .setSlug(club.getSlug())
                .setIsPublic(club.getIsPublic())
                .setClubProfile(club.getClubProfile().orElse(null))
                .setDeletedAt(club.getDeletedAt())
                .setClubAccount(null)
                .build();
    }

    public static ClubView of(ClubAccount clubAccount) {
        Club club = Optional.ofNullable(clubAccount.getClub())
                .orElseThrow(InternalServerErrorException::new);

        return ClubView.of(club).toBuilder()
                .setClubAccount(ClubAccountView.of(clubAccount))
                .build();
    }
}
