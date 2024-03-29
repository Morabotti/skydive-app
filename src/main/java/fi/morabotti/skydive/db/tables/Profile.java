/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.tables.records.ProfileRecord;

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
public class Profile extends TableImpl<ProfileRecord> {

    private static final long serialVersionUID = -1797840090;

    /**
     * The reference instance of <code>profile</code>
     */
    public static final Profile PROFILE = new Profile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProfileRecord> getRecordType() {
        return ProfileRecord.class;
    }

    /**
     * The column <code>profile.id</code>.
     */
    public final TableField<ProfileRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>profile.first_name</code>.
     */
    public final TableField<ProfileRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.last_name</code>.
     */
    public final TableField<ProfileRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.address</code>.
     */
    public final TableField<ProfileRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.zipcode</code>.
     */
    public final TableField<ProfileRecord, String> ZIPCODE = createField("zipcode", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.city</code>.
     */
    public final TableField<ProfileRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.phone</code>.
     */
    public final TableField<ProfileRecord, String> PHONE = createField("phone", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>profile.deleted_at</code>.
     */
    public final TableField<ProfileRecord, Timestamp> DELETED_AT = createField("deleted_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>profile.account_id</code>.
     */
    public final TableField<ProfileRecord, Long> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>profile</code> table reference
     */
    public Profile() {
        this(DSL.name("profile"), null);
    }

    /**
     * Create an aliased <code>profile</code> table reference
     */
    public Profile(String alias) {
        this(DSL.name(alias), PROFILE);
    }

    /**
     * Create an aliased <code>profile</code> table reference
     */
    public Profile(Name alias) {
        this(alias, PROFILE);
    }

    private Profile(Name alias, Table<ProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private Profile(Name alias, Table<ProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Profile(Table<O> child, ForeignKey<O, ProfileRecord> key) {
        super(child, key, PROFILE);
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
        return Arrays.<Index>asList(Indexes.PROFILE_FK_PROFILE_ACCOUNT, Indexes.PROFILE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ProfileRecord, Long> getIdentity() {
        return Keys.IDENTITY_PROFILE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<ProfileRecord>>asList(Keys.KEY_PROFILE_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ProfileRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ProfileRecord, ?>>asList(Keys.FK_PROFILE_ACCOUNT);
    }

    public Account account() {
        return new Account(this, Keys.FK_PROFILE_ACCOUNT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Profile as(String alias) {
        return new Profile(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Profile as(Name alias) {
        return new Profile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Profile rename(String name) {
        return new Profile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Profile rename(Name name) {
        return new Profile(name, null);
    }
}
