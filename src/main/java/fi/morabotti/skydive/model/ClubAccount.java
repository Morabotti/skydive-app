package fi.morabotti.skydive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.db.tables.records.ClubAccountRecord;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static fi.morabotti.skydive.db.tables.ClubAccount.CLUB_ACCOUNT;

@EasyValue
@JsonDeserialize(builder = ClubAccount.Builder.class)
public abstract class ClubAccount {
    @EasyId
    public abstract Long getId();

    public abstract ClubAccountRole getRole();

    @Nullable
    public abstract Account getAccount();

    @Nullable
    public abstract Instant getCreatedAt();

    @JsonIgnore
    public abstract Club getClub();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_ClubAccount.Builder {
    }

    public static final ClubAccountRecordMapper<ClubAccountRecord> mapper
            = ClubAccountRecordMapper.builder(CLUB_ACCOUNT)
            .setIdAccessor(CLUB_ACCOUNT.ID)
            .setAccountAccessor(CLUB_ACCOUNT.ACCOUNT_ID, Account::getId)
            .setClubAccessor(CLUB_ACCOUNT.CLUB_ID, Club::getId)
            .setRoleAccessor(CLUB_ACCOUNT.ROLE)
            .setCreatedAtAccessor(
                    CLUB_ACCOUNT.CREATED_AT,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}
