package fi.morabotti.skydive.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.model.Account;
import fi.morabotti.skydive.model.Profile;

import javax.annotation.Nullable;
import java.time.Instant;

@EasyValue
@JsonDeserialize(builder = AccountView.Builder.class)
public abstract class AccountView {
    public abstract Long getId();

    public abstract String getUsername();

    public abstract AccountRole getRole();

    @Nullable
    public abstract Profile getProfile();

    @Nullable
    public abstract Instant getDeletedAt();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_AccountView.Builder {
    }

    public static AccountView of(Account account) {
        return AccountView.builder()
                .setId(account.getId())
                .setUsername(account.getUsername())
                .setRole(account.getAccountRole())
                .setProfile(account.getProfile().orElse(null))
                .setDeletedAt(account.getDeletedAt())
                .build();
    }
}
