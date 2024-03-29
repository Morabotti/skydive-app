/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables.records;


import fi.morabotti.skydive.db.tables.Profile;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record9;
import org.jooq.Row9;
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
public class ProfileRecord extends TableRecordImpl<ProfileRecord> implements Record9<Long, String, String, String, String, String, String, Timestamp, Long> {

    private static final long serialVersionUID = 972259533;

    /**
     * Setter for <code>profile.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>profile.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>profile.first_name</code>.
     */
    public void setFirstName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>profile.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>profile.last_name</code>.
     */
    public void setLastName(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>profile.last_name</code>.
     */
    public String getLastName() {
        return (String) get(2);
    }

    /**
     * Setter for <code>profile.address</code>.
     */
    public void setAddress(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>profile.address</code>.
     */
    public String getAddress() {
        return (String) get(3);
    }

    /**
     * Setter for <code>profile.zipcode</code>.
     */
    public void setZipcode(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>profile.zipcode</code>.
     */
    public String getZipcode() {
        return (String) get(4);
    }

    /**
     * Setter for <code>profile.city</code>.
     */
    public void setCity(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>profile.city</code>.
     */
    public String getCity() {
        return (String) get(5);
    }

    /**
     * Setter for <code>profile.phone</code>.
     */
    public void setPhone(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>profile.phone</code>.
     */
    public String getPhone() {
        return (String) get(6);
    }

    /**
     * Setter for <code>profile.deleted_at</code>.
     */
    public void setDeletedAt(Timestamp value) {
        set(7, value);
    }

    /**
     * Getter for <code>profile.deleted_at</code>.
     */
    public Timestamp getDeletedAt() {
        return (Timestamp) get(7);
    }

    /**
     * Setter for <code>profile.account_id</code>.
     */
    public void setAccountId(Long value) {
        set(8, value);
    }

    /**
     * Getter for <code>profile.account_id</code>.
     */
    public Long getAccountId() {
        return (Long) get(8);
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Long, String, String, String, String, String, String, Timestamp, Long> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<Long, String, String, String, String, String, String, Timestamp, Long> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Profile.PROFILE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Profile.PROFILE.FIRST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Profile.PROFILE.LAST_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Profile.PROFILE.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Profile.PROFILE.ZIPCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Profile.PROFILE.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Profile.PROFILE.PHONE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field8() {
        return Profile.PROFILE.DELETED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field9() {
        return Profile.PROFILE.ACCOUNT_ID;
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
        return getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getZipcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component8() {
        return getDeletedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component9() {
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
    public String value2() {
        return getFirstName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getLastName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getZipcode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getPhone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value8() {
        return getDeletedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value9() {
        return getAccountId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value2(String value) {
        setFirstName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value3(String value) {
        setLastName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value4(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value5(String value) {
        setZipcode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value6(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value7(String value) {
        setPhone(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value8(Timestamp value) {
        setDeletedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord value9(Long value) {
        setAccountId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProfileRecord values(Long value1, String value2, String value3, String value4, String value5, String value6, String value7, Timestamp value8, Long value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProfileRecord
     */
    public ProfileRecord() {
        super(Profile.PROFILE);
    }

    /**
     * Create a detached, initialised ProfileRecord
     */
    public ProfileRecord(Long id, String firstName, String lastName, String address, String zipcode, String city, String phone, Timestamp deletedAt, Long accountId) {
        super(Profile.PROFILE);

        set(0, id);
        set(1, firstName);
        set(2, lastName);
        set(3, address);
        set(4, zipcode);
        set(5, city);
        set(6, phone);
        set(7, deletedAt);
        set(8, accountId);
    }
}
