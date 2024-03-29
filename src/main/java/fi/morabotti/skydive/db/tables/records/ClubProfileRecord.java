/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables.records;


import fi.morabotti.skydive.db.tables.ClubProfile;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record8;
import org.jooq.Row8;
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
public class ClubProfileRecord extends TableRecordImpl<ClubProfileRecord> implements Record8<Long, String, String, String, String, String, Timestamp, Long> {

    private static final long serialVersionUID = 774282773;

    /**
     * Setter for <code>club_profile.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>club_profile.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>club_profile.description</code>.
     */
    public void setDescription(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>club_profile.description</code>.
     */
    public String getDescription() {
        return (String) get(1);
    }

    /**
     * Setter for <code>club_profile.address</code>.
     */
    public void setAddress(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>club_profile.address</code>.
     */
    public String getAddress() {
        return (String) get(2);
    }

    /**
     * Setter for <code>club_profile.zipcode</code>.
     */
    public void setZipcode(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>club_profile.zipcode</code>.
     */
    public String getZipcode() {
        return (String) get(3);
    }

    /**
     * Setter for <code>club_profile.city</code>.
     */
    public void setCity(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>club_profile.city</code>.
     */
    public String getCity() {
        return (String) get(4);
    }

    /**
     * Setter for <code>club_profile.phone</code>.
     */
    public void setPhone(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>club_profile.phone</code>.
     */
    public String getPhone() {
        return (String) get(5);
    }

    /**
     * Setter for <code>club_profile.deleted_at</code>.
     */
    public void setDeletedAt(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>club_profile.deleted_at</code>.
     */
    public Timestamp getDeletedAt() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>club_profile.club_id</code>.
     */
    public void setClubId(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>club_profile.club_id</code>.
     */
    public Long getClubId() {
        return (Long) get(7);
    }

    // -------------------------------------------------------------------------
    // Record8 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, String, String, Timestamp, Long> fieldsRow() {
        return (Row8) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row8<Long, String, String, String, String, String, Timestamp, Long> valuesRow() {
        return (Row8) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return ClubProfile.CLUB_PROFILE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ClubProfile.CLUB_PROFILE.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ClubProfile.CLUB_PROFILE.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return ClubProfile.CLUB_PROFILE.ZIPCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return ClubProfile.CLUB_PROFILE.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return ClubProfile.CLUB_PROFILE.PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return ClubProfile.CLUB_PROFILE.DELETED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return ClubProfile.CLUB_PROFILE.CLUB_ID;
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
    public String component2() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getZipcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component7() {
        return getDeletedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component8() {
        return getClubId();
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
    public String value2() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getZipcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getDeletedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getClubId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value2(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value3(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value4(String value) {
        setZipcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value5(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value6(String value) {
        setPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value7(Timestamp value) {
        setDeletedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord value8(Long value) {
        setClubId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfileRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, Timestamp value7, Long value8) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ClubProfileRecord
     */
    public ClubProfileRecord() {
        super(ClubProfile.CLUB_PROFILE);
    }

    /**
     * Create a detached, initialised ClubProfileRecord
     */
    public ClubProfileRecord(Long id, String description, String address, String zipcode, String city, String phone, Timestamp deletedAt, Long clubId) {
        super(ClubProfile.CLUB_PROFILE);

        set(0, id);
        set(1, description);
        set(2, address);
        set(3, zipcode);
        set(4, city);
        set(5, phone);
        set(6, deletedAt);
        set(7, clubId);
    }
}
