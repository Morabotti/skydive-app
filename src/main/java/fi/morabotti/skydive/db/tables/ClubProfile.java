/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.tables.records.ClubProfileRecord;

import java.sql.Timestamp;
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
public class ClubProfile extends TableImpl<ClubProfileRecord> {

    private static final long serialVersionUID = 1355053938;

    /**
     * The reference instance of <code>club_profile</code>
     */
    public static final ClubProfile CLUB_PROFILE = new ClubProfile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClubProfileRecord> getRecordType() {
        return ClubProfileRecord.class;
    }

    /**
     * The column <code>club_profile.id</code>.
     */
    public final TableField<ClubProfileRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>club_profile.description</code>.
     */
    public final TableField<ClubProfileRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>club_profile.address</code>.
     */
    public final TableField<ClubProfileRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>club_profile.zipcode</code>.
     */
    public final TableField<ClubProfileRecord, String> ZIPCODE = createField("zipcode", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>club_profile.city</code>.
     */
    public final TableField<ClubProfileRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>club_profile.phone</code>.
     */
    public final TableField<ClubProfileRecord, String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>club_profile.deleted_at</code>.
     */
    public final TableField<ClubProfileRecord, Timestamp> DELETED_AT = createField("deleted_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>club_profile.club_id</code>.
     */
    public final TableField<ClubProfileRecord, Long> CLUB_ID = createField("club_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>club_profile</code> table reference
     */
    public ClubProfile() {
        this(DSL.name("club_profile"), null);
    }

    /**
     * Create an aliased <code>club_profile</code> table reference
     */
    public ClubProfile(String alias) {
        this(DSL.name(alias), CLUB_PROFILE);
    }

    /**
     * Create an aliased <code>club_profile</code> table reference
     */
    public ClubProfile(Name alias) {
        this(alias, CLUB_PROFILE);
    }

    private ClubProfile(Name alias, Table<ClubProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private ClubProfile(Name alias, Table<ClubProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ClubProfile(Table<O> child, ForeignKey<O, ClubProfileRecord> key) {
        super(child, key, CLUB_PROFILE);
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
        return Arrays.<Index>asList(Indexes.CLUB_PROFILE_FK_CLUB_PROFILE_CLUB, Indexes.CLUB_PROFILE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ClubProfileRecord, Long> getIdentity() {
        return Keys.IDENTITY_CLUB_PROFILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ClubProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<ClubProfileRecord>>asList(Keys.KEY_CLUB_PROFILE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ClubProfileRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ClubProfileRecord, ?>>asList(Keys.FK_CLUB_PROFILE_CLUB);
    }

    public Club club() {
        return new Club(this, Keys.FK_CLUB_PROFILE_CLUB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfile as(String alias) {
        return new ClubProfile(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubProfile as(Name alias) {
        return new ClubProfile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ClubProfile rename(String name) {
        return new ClubProfile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ClubProfile rename(Name name) {
        return new ClubProfile(name, null);
    }
}
