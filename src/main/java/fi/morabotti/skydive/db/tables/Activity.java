/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.enums.ActivityAccess;
import fi.morabotti.skydive.db.enums.ActivityType;
import fi.morabotti.skydive.db.tables.records.ActivityRecord;

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
public class Activity extends TableImpl<ActivityRecord> {

    private static final long serialVersionUID = 744073794;

    /**
     * The reference instance of <code>activity</code>
     */
    public static final Activity ACTIVITY = new Activity();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ActivityRecord> getRecordType() {
        return ActivityRecord.class;
    }

    /**
     * The column <code>activity.id</code>.
     */
    public final TableField<ActivityRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>activity.title</code>.
     */
    public final TableField<ActivityRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>activity.token</code>.
     */
    public final TableField<ActivityRecord, String> TOKEN = createField("token", org.jooq.impl.SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>activity.type</code>.
     */
    public final TableField<ActivityRecord, ActivityType> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(4).nullable(false).asEnumDataType(fi.morabotti.skydive.db.enums.ActivityType.class), this, "");

    /**
     * The column <code>activity.description</code>.
     */
    public final TableField<ActivityRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>activity.access</code>.
     */
    public final TableField<ActivityRecord, ActivityAccess> ACCESS = createField("access", org.jooq.impl.SQLDataType.VARCHAR(6).nullable(false).asEnumDataType(fi.morabotti.skydive.db.enums.ActivityAccess.class), this, "");

    /**
     * The column <code>activity.visible</code>.
     */
    public final TableField<ActivityRecord, Boolean> VISIBLE = createField("visible", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>activity.start_date</code>.
     */
    public final TableField<ActivityRecord, Timestamp> START_DATE = createField("start_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>activity.end_date</code>.
     */
    public final TableField<ActivityRecord, Timestamp> END_DATE = createField("end_date", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activity.created_at</code>.
     */
    public final TableField<ActivityRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>activity.deleted_at</code>.
     */
    public final TableField<ActivityRecord, Timestamp> DELETED_AT = createField("deleted_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>activity.club_id</code>.
     */
    public final TableField<ActivityRecord, Long> CLUB_ID = createField("club_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>activity</code> table reference
     */
    public Activity() {
        this(DSL.name("activity"), null);
    }

    /**
     * Create an aliased <code>activity</code> table reference
     */
    public Activity(String alias) {
        this(DSL.name(alias), ACTIVITY);
    }

    /**
     * Create an aliased <code>activity</code> table reference
     */
    public Activity(Name alias) {
        this(alias, ACTIVITY);
    }

    private Activity(Name alias, Table<ActivityRecord> aliased) {
        this(alias, aliased, null);
    }

    private Activity(Name alias, Table<ActivityRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Activity(Table<O> child, ForeignKey<O, ActivityRecord> key) {
        super(child, key, ACTIVITY);
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
        return Arrays.<Index>asList(Indexes.ACTIVITY_FK_ACTIVITY_CLUB_ID, Indexes.ACTIVITY_ID, Indexes.ACTIVITY_TOKEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ActivityRecord, Long> getIdentity() {
        return Keys.IDENTITY_ACTIVITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ActivityRecord>> getKeys() {
        return Arrays.<UniqueKey<ActivityRecord>>asList(Keys.KEY_ACTIVITY_ID, Keys.KEY_ACTIVITY_TOKEN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ActivityRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ActivityRecord, ?>>asList(Keys.FK_ACTIVITY_CLUB_ID);
    }

    public Club club() {
        return new Club(this, Keys.FK_ACTIVITY_CLUB_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity as(String alias) {
        return new Activity(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity as(Name alias) {
        return new Activity(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Activity rename(String name) {
        return new Activity(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Activity rename(Name name) {
        return new Activity(name, null);
    }
}
