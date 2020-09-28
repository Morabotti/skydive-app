package fi.morabotti.skydive.view;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.model.Account;

@EasyValue
@JsonDeserialize(builder = AccountView.Builder.class)
public abstract class AccountView {
    public abstract String getUsername();

    public abstract AccountRole getRole();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_AccountView.Builder {
    }

    public static AccountView of(Account account) {
        return AccountView.builder()
                .setUsername(account.getUsername())
                .setRole(account.getAccountRole())
                .build();
    }
}
