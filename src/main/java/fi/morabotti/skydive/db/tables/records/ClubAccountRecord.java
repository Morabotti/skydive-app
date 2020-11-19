/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables.records;


import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.db.tables.ClubAccount;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ClubAccountRecord extends TableRecordImpl<ClubAccountRecord> implements Record6<Long, ClubAccountRole, Timestamp, Timestamp, Long, Long> {

    private static final long serialVersionUID = -2008883088;

    /**
     * Setter for <code>club_account.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>club_account.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>club_account.role</code>.
     */
    public void setRole(ClubAccountRole value) {
        set(1, value);
    }

    /**
     * Getter for <code>club_account.role</code>.
     */
    public ClubAccountRole getRole() {
        return (ClubAccountRole) get(1);
    }

    /**
     * Setter for <code>club_account.accepted</code>.
     */
    public void setAccepted(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>club_account.accepted</code>.
     */
    public Timestamp getAccepted() {
        return (Timestamp) get(2);
    }

    /**
     * Setter for <code>club_account.created_at</code>.
     */
    public void setCreatedAt(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>club_account.created_at</code>.
     */
    public Timestamp getCreatedAt() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>club_account.club_id</code>.
     */
    public void setClubId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>club_account.club_id</code>.
     */
    public Long getClubId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>club_account.account_id</code>.
     */
    public void setAccountId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>club_account.account_id</code>.
     */
    public Long getAccountId() {
        return (Long) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, ClubAccountRole, Timestamp, Timestamp, Long, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Long, ClubAccountRole, Timestamp, Timestamp, Long, Long> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ClubAccount.CLUB_ACCOUNT.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<ClubAccountRole> field2() {
        return ClubAccount.CLUB_ACCOUNT.ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return ClubAccount.CLUB_ACCOUNT.ACCEPTED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return ClubAccount.CLUB_ACCOUNT.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return ClubAccount.CLUB_ACCOUNT.CLUB_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return ClubAccount.CLUB_ACCOUNT.ACCOUNT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRole component2() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component3() {
        return getAccepted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getClubId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component6() {
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRole value2() {
        return getRole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getAccepted();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getClubId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value2(ClubAccountRole value) {
        setRole(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value3(Timestamp value) {
        setAccepted(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value4(Timestamp value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value5(Long value) {
        setClubId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord value6(Long value) {
        setAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccountRecord values(Long value1, ClubAccountRole value2, Timestamp value3, Timestamp value4, Long value5, Long value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ClubAccountRecord
     */
    public ClubAccountRecord() {
        super(ClubAccount.CLUB_ACCOUNT);
    }

    /**
     * Create a detached, initialised ClubAccountRecord
     */
    public ClubAccountRecord(Long id, ClubAccountRole role, Timestamp accepted, Timestamp createdAt, Long clubId, Long accountId) {
        super(ClubAccount.CLUB_ACCOUNT);

        set(0, id);
        set(1, role);
        set(2, accepted);
        set(3, createdAt);
        set(4, clubId);
        set(5, accountId);
    }
}
