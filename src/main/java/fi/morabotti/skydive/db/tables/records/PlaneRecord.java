/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables.records;


import fi.morabotti.skydive.db.tables.Plane;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record5;
import org.jooq.Row5;
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
public class PlaneRecord extends TableRecordImpl<PlaneRecord> implements Record5<Long, String, Integer, Timestamp, Long> {

    private static final long serialVersionUID = -1835524325;

    /**
     * Setter for <code>plane.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>plane.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>plane.license_number</code>.
     */
    public void setLicenseNumber(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>plane.license_number</code>.
     */
    public String getLicenseNumber() {
        return (String) get(1);
    }

    /**
     * Setter for <code>plane.seats</code>.
     */
    public void setSeats(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>plane.seats</code>.
     */
    public Integer getSeats() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>plane.deleted_at</code>.
     */
    public void setDeletedAt(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>plane.deleted_at</code>.
     */
    public Timestamp getDeletedAt() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>plane.club_id</code>.
     */
    public void setClubId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>plane.club_id</code>.
     */
    public Long getClubId() {
        return (Long) get(4);
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, Integer, Timestamp, Long> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, Integer, Timestamp, Long> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Plane.PLANE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Plane.PLANE.LICENSE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return Plane.PLANE.SEATS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return Plane.PLANE.DELETED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return Plane.PLANE.CLUB_ID;
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
        return getLicenseNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component3() {
        return getSeats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component4() {
        return getDeletedAt();
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
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getLicenseNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getSeats();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getDeletedAt();
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
    public PlaneRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneRecord value2(String value) {
        setLicenseNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneRecord value3(Integer value) {
        setSeats(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneRecord value4(Timestamp value) {
        setDeletedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneRecord value5(Long value) {
        setClubId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlaneRecord values(Long value1, String value2, Integer value3, Timestamp value4, Long value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached PlaneRecord
     */
    public PlaneRecord() {
        super(Plane.PLANE);
    }

    /**
     * Create a detached, initialised PlaneRecord
     */
    public PlaneRecord(Long id, String licenseNumber, Integer seats, Timestamp deletedAt, Long clubId) {
        super(Plane.PLANE);

        set(0, id);
        set(1, licenseNumber);
        set(2, seats);
        set(3, deletedAt);
        set(4, clubId);
    }
}
