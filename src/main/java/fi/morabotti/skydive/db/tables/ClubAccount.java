/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db.tables;


import fi.morabotti.skydive.db.DefaultSchema;
import fi.morabotti.skydive.db.Indexes;
import fi.morabotti.skydive.db.Keys;
import fi.morabotti.skydive.db.enums.ClubAccountRole;
import fi.morabotti.skydive.db.tables.records.ClubAccountRecord;

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
public class ClubAccount extends TableImpl<ClubAccountRecord> {

    private static final long serialVersionUID = -1014645281;

    /**
     * The reference instance of <code>club_account</code>
     */
    public static final ClubAccount CLUB_ACCOUNT = new ClubAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClubAccountRecord> getRecordType() {
        return ClubAccountRecord.class;
    }

    /**
     * The column <code>club_account.id</code>.
     */
    public final TableField<ClubAccountRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>club_account.role</code>.
     */
    public final TableField<ClubAccountRecord, ClubAccountRole> ROLE = createField("role", org.jooq.impl.SQLDataType.VARCHAR(5).nullable(false).asEnumDataType(fi.morabotti.skydive.db.enums.ClubAccountRole.class), this, "");

    /**
     * The column <code>club_account.accepted</code>.
     */
    public final TableField<ClubAccountRecord, Boolean> ACCEPTED = createField("accepted", org.jooq.impl.SQLDataType.BIT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("b'0'", org.jooq.impl.SQLDataType.BIT)), this, "");

    /**
     * The column <code>club_account.created_at</code>.
     */
    public final TableField<ClubAccountRecord, Timestamp> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>club_account.club_id</code>.
     */
    public final TableField<ClubAccountRecord, Long> CLUB_ID = createField("club_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>club_account.account_id</code>.
     */
    public final TableField<ClubAccountRecord, Long> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>club_account</code> table reference
     */
    public ClubAccount() {
        this(DSL.name("club_account"), null);
    }

    /**
     * Create an aliased <code>club_account</code> table reference
     */
    public ClubAccount(String alias) {
        this(DSL.name(alias), CLUB_ACCOUNT);
    }

    /**
     * Create an aliased <code>club_account</code> table reference
     */
    public ClubAccount(Name alias) {
        this(alias, CLUB_ACCOUNT);
    }

    private ClubAccount(Name alias, Table<ClubAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private ClubAccount(Name alias, Table<ClubAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> ClubAccount(Table<O> child, ForeignKey<O, ClubAccountRecord> key) {
        super(child, key, CLUB_ACCOUNT);
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
        return Arrays.<Index>asList(Indexes.CLUB_ACCOUNT_ACCOUNT_ID, Indexes.CLUB_ACCOUNT_FK_CLUB_ACCOUNT_CLUB_ID, Indexes.CLUB_ACCOUNT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ClubAccountRecord, Long> getIdentity() {
        return Keys.IDENTITY_CLUB_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ClubAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<ClubAccountRecord>>asList(Keys.KEY_CLUB_ACCOUNT_ID, Keys.KEY_CLUB_ACCOUNT_ACCOUNT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<ClubAccountRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<ClubAccountRecord, ?>>asList(Keys.FK_CLUB_ACCOUNT_CLUB_ID, Keys.FK_CLUB_ACCOUNT_ACCOUNT_ID);
    }

    public Club club() {
        return new Club(this, Keys.FK_CLUB_ACCOUNT_CLUB_ID);
    }

    public Account account() {
        return new Account(this, Keys.FK_CLUB_ACCOUNT_ACCOUNT_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccount as(String alias) {
        return new ClubAccount(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClubAccount as(Name alias) {
        return new ClubAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ClubAccount rename(String name) {
        return new ClubAccount(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ClubAccount rename(Name name) {
        return new ClubAccount(name, null);
    }
}
