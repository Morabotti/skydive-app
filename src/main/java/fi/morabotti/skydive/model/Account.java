package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.jubic.snoozy.auth.UserPrincipal;
import fi.morabotti.skydive.db.enums.AccountRole;
import fi.morabotti.skydive.db.tables.records.AccountRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static fi.morabotti.skydive.db.tables.Account.ACCOUNT;

@EasyValue
public abstract class Account implements UserPrincipal {
    @EasyId
    public abstract Long getId();

    public abstract String getUsername();

    public abstract List<Profile> getProfiles();

    @JsonIgnore
    public abstract byte[] getPasswordHash();

    @JsonIgnore
    public abstract byte[] getPasswordSalt();

    @JsonIgnore
    public abstract AccountRole getAccountRole();

    @Nullable
    public abstract Instant getDeletedAt();

    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public String getRole() {
        return getAccountRole().getLiteral();
    }

    public boolean isDeleted() {
        return getDeletedAt() != null;
    }

    @JsonIgnore
    public Optional<Profile> getProfile() {
        return getProfiles().stream().findFirst();
    }

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Account.Builder {
        public Builder defaults(Builder builder) {
            return builder.setProfiles(Collections.emptyList());
        }
    }

    public static final AccountRecordMapper<AccountRecord> mapper
            = AccountRecordMapper.builder(ACCOUNT)
            .setIdAccessor(ACCOUNT.ID)
            .setUsernameAccessor(ACCOUNT.USERNAME)
            .setPasswordHashAccessor(ACCOUNT.PASSWORD_HASH)
            .setPasswordSaltAccessor(ACCOUNT.PASSWORD_SALT)
            .setAccountRoleAccessor(ACCOUNT.ROLE)
            .setDeletedAtAccessor(
                    ACCOUNT.DELETED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}

