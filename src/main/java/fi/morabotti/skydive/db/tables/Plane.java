/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.tables.records.PlaneRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class Plane extends TableImpl<PlaneRecord> {

    private static final long serialVersionUID = -1738593671;

    /**
     * The reference instance of <code>plane</code>
     */
    public static final Plane PLANE = new Plane();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlaneRecord> getRecordType() {
        return PlaneRecord.class;
    }

    /**
     * The column <code>plane.id</code>.
     */
    public final TableField<PlaneRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>plane.license_number</code>.
     */
    public final TableField<PlaneRecord, String> LICENSE_NUMBER = createField("license_number", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>plane.seats</code>.
     */
    public final TableField<PlaneRecord, Integer> SEATS = createField("seats", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>plane.club_id</code>.
     */
    public final TableField<PlaneRecord, Long> CLUB_ID = createField("club_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>plane</code> table reference
     */
    public Plane() {
        this(DSL.name("plane"), null);
    }

    /**
     * Create an aliased <code>plane</code> table reference
     */
    public Plane(String alias) {
        this(DSL.name(alias), PLANE);
    }

    /**
     * Create an aliased <code>plane</code> table reference
     */
    public Plane(Name alias) {
        this(alias, PLANE);
    }

    private Plane(Name alias, Table<PlaneRecord> aliased) {
        this(alias, aliased, null);
    }

    private Plane(Name alias, Table<PlaneRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Plane(Table<O> child, ForeignKey<O, PlaneRecord> key) {
        super(child, key, PLANE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PLANE_FK_PLANE_CLUB_ID, Indexes.PLANE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PlaneRecord, Long> getIdentity() {
        return Keys.IDENTITY_PLANE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PlaneRecord>> getKeys() {
        return Arrays.<UniqueKey<PlaneRecord>>asList(Keys.KEY_PLANE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<PlaneRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<PlaneRecord, ?>>asList(Keys.FK_PLANE_CLUB_ID);
    }

    public Club club() {
        return new Club(this, Keys.FK_PLANE_CLUB_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plane as(String alias) {
        return new Plane(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plane as(Name alias) {
        return new Plane(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Plane rename(String name) {
        return new Plane(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Plane rename(Name name) {
        return new Plane(name, null);
    }
}
