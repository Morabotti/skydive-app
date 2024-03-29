/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.tables.records.JumpRecord;

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
public class Jump extends TableImpl<JumpRecord> {

    private static final long serialVersionUID = -491994978;

    /**
     * The reference instance of <code>jump</code>
     */
    public static final Jump JUMP = new Jump();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<JumpRecord> getRecordType() {
        return JumpRecord.class;
    }

    /**
     * The column <code>jump.id</code>.
     */
    public final TableField<JumpRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>jump.jump_height</code>.
     */
    public final TableField<JumpRecord, Integer> JUMP_HEIGHT = createField("jump_height", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>jump.jump_time</code>.
     */
    public final TableField<JumpRecord, Timestamp> JUMP_TIME = createField("jump_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>jump.account_id</code>.
     */
    public final TableField<JumpRecord, Long> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>jump.activity_id</code>.
     */
    public final TableField<JumpRecord, Long> ACTIVITY_ID = createField("activity_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>jump.created_at</code>.
     */
    public final TableField<JumpRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>jump.deleted_at</code>.
     */
    public final TableField<JumpRecord, Timestamp> DELETED_AT = createField("deleted_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * Create a <code>jump</code> table reference
     */
    public Jump() {
        this(DSL.name("jump"), null);
    }

    /**
     * Create an aliased <code>jump</code> table reference
     */
    public Jump(String alias) {
        this(DSL.name(alias), JUMP);
    }

    /**
     * Create an aliased <code>jump</code> table reference
     */
    public Jump(Name alias) {
        this(alias, JUMP);
    }

    private Jump(Name alias, Table<JumpRecord> aliased) {
        this(alias, aliased, null);
    }

    private Jump(Name alias, Table<JumpRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Jump(Table<O> child, ForeignKey<O, JumpRecord> key) {
        super(child, key, JUMP);
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
        return Arrays.<Index>asList(Indexes.JUMP_FK_JUMP_ACCOUNT_ID, Indexes.JUMP_FK_JUMP_ACTIVITY_ID, Indexes.JUMP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<JumpRecord, Long> getIdentity() {
        return Keys.IDENTITY_JUMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<JumpRecord>> getKeys() {
        return Arrays.<UniqueKey<JumpRecord>>asList(Keys.KEY_JUMP_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<JumpRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<JumpRecord, ?>>asList(Keys.FK_JUMP_ACCOUNT_ID, Keys.FK_JUMP_ACTIVITY_ID);
    }

    public Account account() {
        return new Account(this, Keys.FK_JUMP_ACCOUNT_ID);
    }

    public Activity activity() {
        return new Activity(this, Keys.FK_JUMP_ACTIVITY_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Jump as(String alias) {
        return new Jump(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Jump as(Name alias) {
        return new Jump(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Jump rename(String name) {
        return new Jump(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Jump rename(Name name) {
        return new Jump(name, null);
    }
}
