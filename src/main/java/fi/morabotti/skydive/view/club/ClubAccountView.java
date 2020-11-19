package fi.morabotti.skydive.view.club;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.model.ClubAccount;
import fi.morabotti.skydive.view.AccountView;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@EasyValue
@JsonDeserialize(builder = ClubAccountView.Builder.class)
public abstract class ClubAccountView {
    public abstract Long getId();

    public abstract ClubAccountRole getRole();

    @Nullable
    public abstract Instant getAccepted();

    @Nullable
    public abstract AccountView getAccount();

    @Nullable
    public abstract ClubView getClub();

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
                .setAccount(Optional.ofNullable(clubAccount.getAccount())
                        .map(AccountView::of)
                        .orElse(null)
                )
                .setClub(Optional.ofNullable(clubAccount.getClub())
                        .map(ClubView::of)
                        .orElse(null)
                )
                .build();
    }
}
