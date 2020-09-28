package fi.morabotti.skydive.model;

import fi.jubic.easymapper.annotations.EasyId;
import fi.jubic.easyvalue.EasyValue;
import fi.morabotti.skydive.db.tables.records.SessionRecord;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static fi.morabotti.skydive.db.tables.Session.SESSION;

@EasyValue
public abstract class Session {
    @EasyId
    public abstract Long getId();

    public abstract UUID getToken();

    public abstract Instant getValidUntil();

    public abstract Account getAccount();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EasyValue_Session.Builder {
    }

    public static final SessionRecordMapper<SessionRecord> mapper
            = SessionRecordMapper.builder(SESSION)
            .setIdAccessor(SESSION.ID)
            .setAccountAccessor(SESSION.ACCOUNT_ID, Account::getId)
            .setTokenAccessor(
                    SESSION.TOKEN,
                    UUID::toString,
                    UUID::fromString
            )
            .setValidUntilAccessor(
                    SESSION.VALID_UNTIL,
                    Timestamp::from,
                    Timestamp::toInstant
            )
            .build();
}
