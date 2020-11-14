package fi.morabotti.skydive.view.club;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.model.ClubAccount;

import javax.annotation.Nullable;
import java.time.Instant;

@EasyValue
@JsonDeserialize(builder = ClubAccountView.Builder.class)
public abstract class ClubAccountView {
    public abstract Long getId();

    public abstract ClubAccountRole getRole();

    @Nullable
    public abstract Boolean getAccepted();

    @Nullable
    public abstract Instant getCreatedAt();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubAccountView.Builder {

    }

    public static ClubAccountView of(ClubAccount clubAccount) {
        return ClubAccountView.builder()
                .setId(clubAccount.getId())
                .setAccepted(clubAccount.getAccepted())
                .setCreatedAt(clubAccount.getCreatedAt())
                .setRole(clubAccount.getRole())
                .build();
    }
}
