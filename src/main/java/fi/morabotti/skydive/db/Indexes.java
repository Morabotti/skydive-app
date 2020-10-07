/*
 * This file is generated by jOOQ.
 */
package fi.morabotti.skydive.db;


import fi.morabotti.skydive.db.tables.Account;
import fi.morabotti.skydive.db.tables.Club;
import fi.morabotti.skydive.db.tables.ClubAccount;
import fi.morabotti.skydive.db.tables.ClubActivity;
import fi.morabotti.skydive.db.tables.ClubActivityParticipation;
import fi.morabotti.skydive.db.tables.ClubPilotActivityParticipation;
import fi.morabotti.skydive.db.tables.ClubProfile;
import fi.morabotti.skydive.db.tables.Jump;
import fi.morabotti.skydive.db.tables.Plane;
import fi.morabotti.skydive.db.tables.Profile;
import fi.morabotti.skydive.db.tables.Session;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.11"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACCOUNT_ID = Indexes0.ACCOUNT_ID;
    public static final Index ACCOUNT_USERNAME = Indexes0.ACCOUNT_USERNAME;
    public static final Index CLUB_FK_CLUB_CREATOR_ACCOUNT = Indexes0.CLUB_FK_CLUB_CREATOR_ACCOUNT;
    public static final Index CLUB_ID = Indexes0.CLUB_ID;
    public static final Index CLUB_SLUG = Indexes0.CLUB_SLUG;
    public static final Index CLUB_ACCOUNT_ACCOUNT_ID = Indexes0.CLUB_ACCOUNT_ACCOUNT_ID;
    public static final Index CLUB_ACCOUNT_FK_CLUB_ACCOUNT_CLUB_ID = Indexes0.CLUB_ACCOUNT_FK_CLUB_ACCOUNT_CLUB_ID;
    public static final Index CLUB_ACCOUNT_ID = Indexes0.CLUB_ACCOUNT_ID;
    public static final Index CLUB_ACTIVITY_FK_CLUB_ACTIVITY_CLUB_ID = Indexes0.CLUB_ACTIVITY_FK_CLUB_ACTIVITY_CLUB_ID;
    public static final Index CLUB_ACTIVITY_ID = Indexes0.CLUB_ACTIVITY_ID;
    public static final Index CLUB_ACTIVITY_PARTICIPATION_ACCOUNT_ID = Indexes0.CLUB_ACTIVITY_PARTICIPATION_ACCOUNT_ID;
    public static final Index CLUB_ACTIVITY_PARTICIPATION_FK_CLUB_ACTIVITY_PARTICIPATION_ACTIVITY_ID = Indexes0.CLUB_ACTIVITY_PARTICIPATION_FK_CLUB_ACTIVITY_PARTICIPATION_ACTIVITY_ID;
    public static final Index CLUB_ACTIVITY_PARTICIPATION_ID = Indexes0.CLUB_ACTIVITY_PARTICIPATION_ID;
    public static final Index CLUB_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID = Indexes0.CLUB_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID;
    public static final Index CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_ACTIVITY_ID = Indexes0.CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_ACTIVITY_ID;
    public static final Index CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID = Indexes0.CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID;
    public static final Index CLUB_PILOT_ACTIVITY_PARTICIPATION_ID = Indexes0.CLUB_PILOT_ACTIVITY_PARTICIPATION_ID;
    public static final Index CLUB_PROFILE_FK_CLUB_PROFILE_CLUB = Indexes0.CLUB_PROFILE_FK_CLUB_PROFILE_CLUB;
    public static final Index CLUB_PROFILE_ID = Indexes0.CLUB_PROFILE_ID;
    public static final Index JUMP_FK_JUMP_ACCOUNT_ID = Indexes0.JUMP_FK_JUMP_ACCOUNT_ID;
    public static final Index JUMP_FK_JUMP_CLUB_ACTIVITY_ID = Indexes0.JUMP_FK_JUMP_CLUB_ACTIVITY_ID;
    public static final Index JUMP_ID = Indexes0.JUMP_ID;
    public static final Index PLANE_FK_PLANE_CLUB_ID = Indexes0.PLANE_FK_PLANE_CLUB_ID;
    public static final Index PLANE_ID = Indexes0.PLANE_ID;
    public static final Index PROFILE_FK_PROFILE_ACCOUNT = Indexes0.PROFILE_FK_PROFILE_ACCOUNT;
    public static final Index PROFILE_ID = Indexes0.PROFILE_ID;
    public static final Index SESSION_FK_SESSION_ACCOUNT = Indexes0.SESSION_FK_SESSION_ACCOUNT;
    public static final Index SESSION_ID = Indexes0.SESSION_ID;
    public static final Index SESSION_TOKEN = Indexes0.SESSION_TOKEN;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ACCOUNT_ID = Internal.createIndex("id", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.ID }, true);
        public static Index ACCOUNT_USERNAME = Internal.createIndex("username", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.USERNAME }, true);
        public static Index CLUB_FK_CLUB_CREATOR_ACCOUNT = Internal.createIndex("fk_club_creator_account", Club.CLUB, new OrderField[] { Club.CLUB.CREATOR_ACCOUNT_ID }, false);
        public static Index CLUB_ID = Internal.createIndex("id", Club.CLUB, new OrderField[] { Club.CLUB.ID }, true);
        public static Index CLUB_SLUG = Internal.createIndex("slug", Club.CLUB, new OrderField[] { Club.CLUB.SLUG }, true);
        public static Index CLUB_ACCOUNT_ACCOUNT_ID = Internal.createIndex("account_id", ClubAccount.CLUB_ACCOUNT, new OrderField[] { ClubAccount.CLUB_ACCOUNT.ACCOUNT_ID, ClubAccount.CLUB_ACCOUNT.CLUB_ID }, true);
        public static Index CLUB_ACCOUNT_FK_CLUB_ACCOUNT_CLUB_ID = Internal.createIndex("fk_club_account_club_id", ClubAccount.CLUB_ACCOUNT, new OrderField[] { ClubAccount.CLUB_ACCOUNT.CLUB_ID }, false);
        public static Index CLUB_ACCOUNT_ID = Internal.createIndex("id", ClubAccount.CLUB_ACCOUNT, new OrderField[] { ClubAccount.CLUB_ACCOUNT.ID }, true);
        public static Index CLUB_ACTIVITY_FK_CLUB_ACTIVITY_CLUB_ID = Internal.createIndex("fk_club_activity_club_id", ClubActivity.CLUB_ACTIVITY, new OrderField[] { ClubActivity.CLUB_ACTIVITY.CLUB_ID }, false);
        public static Index CLUB_ACTIVITY_ID = Internal.createIndex("id", ClubActivity.CLUB_ACTIVITY, new OrderField[] { ClubActivity.CLUB_ACTIVITY.ID }, true);
        public static Index CLUB_ACTIVITY_PARTICIPATION_ACCOUNT_ID = Internal.createIndex("account_id", ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION, new OrderField[] { ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION.ACCOUNT_ID, ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION.CLUB_ACTIVITY_ID }, true);
        public static Index CLUB_ACTIVITY_PARTICIPATION_FK_CLUB_ACTIVITY_PARTICIPATION_ACTIVITY_ID = Internal.createIndex("fk_club_activity_participation_activity_id", ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION, new OrderField[] { ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION.CLUB_ACTIVITY_ID }, false);
        public static Index CLUB_ACTIVITY_PARTICIPATION_ID = Internal.createIndex("id", ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION, new OrderField[] { ClubActivityParticipation.CLUB_ACTIVITY_PARTICIPATION.ID }, true);
        public static Index CLUB_PILOT_ACTIVITY_PARTICIPATION_ACCOUNT_ID = Internal.createIndex("account_id", ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION, new OrderField[] { ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION.ACCOUNT_ID, ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION.CLUB_ACTIVITY_ID }, true);
        public static Index CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_ACTIVITY_ID = Internal.createIndex("fk_club_pilot_activity_participation_activity_id", ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION, new OrderField[] { ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION.CLUB_ACTIVITY_ID }, false);
        public static Index CLUB_PILOT_ACTIVITY_PARTICIPATION_FK_CLUB_PILOT_ACTIVITY_PARTICIPATION_PLANE_ID = Internal.createIndex("fk_club_pilot_activity_participation_plane_id", ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION, new OrderField[] { ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION.PLANE_ID }, false);
        public static Index CLUB_PILOT_ACTIVITY_PARTICIPATION_ID = Internal.createIndex("id", ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION, new OrderField[] { ClubPilotActivityParticipation.CLUB_PILOT_ACTIVITY_PARTICIPATION.ID }, true);
        public static Index CLUB_PROFILE_FK_CLUB_PROFILE_CLUB = Internal.createIndex("fk_club_profile_club", ClubProfile.CLUB_PROFILE, new OrderField[] { ClubProfile.CLUB_PROFILE.CLUB_ID }, false);
        public static Index CLUB_PROFILE_ID = Internal.createIndex("id", ClubProfile.CLUB_PROFILE, new OrderField[] { ClubProfile.CLUB_PROFILE.ID }, true);
        public static Index JUMP_FK_JUMP_ACCOUNT_ID = Internal.createIndex("fk_jump_account_id", Jump.JUMP, new OrderField[] { Jump.JUMP.ACCOUNT_ID }, false);
        public static Index JUMP_FK_JUMP_CLUB_ACTIVITY_ID = Internal.createIndex("fk_jump_club_activity_id", Jump.JUMP, new OrderField[] { Jump.JUMP.CLUB_ACTIVITY_ID }, false);
        public static Index JUMP_ID = Internal.createIndex("id", Jump.JUMP, new OrderField[] { Jump.JUMP.ID }, true);
        public static Index PLANE_FK_PLANE_CLUB_ID = Internal.createIndex("fk_plane_club_id", Plane.PLANE, new OrderField[] { Plane.PLANE.CLUB_ID }, false);
        public static Index PLANE_ID = Internal.createIndex("id", Plane.PLANE, new OrderField[] { Plane.PLANE.ID }, true);
        public static Index PROFILE_FK_PROFILE_ACCOUNT = Internal.createIndex("fk_profile_account", Profile.PROFILE, new OrderField[] { Profile.PROFILE.ACCOUNT_ID }, false);
        public static Index PROFILE_ID = Internal.createIndex("id", Profile.PROFILE, new OrderField[] { Profile.PROFILE.ID }, true);
        public static Index SESSION_FK_SESSION_ACCOUNT = Internal.createIndex("fk_session_account", Session.SESSION, new OrderField[] { Session.SESSION.ACCOUNT_ID }, false);
        public static Index SESSION_ID = Internal.createIndex("id", Session.SESSION, new OrderField[] { Session.SESSION.ID }, true);
        public static Index SESSION_TOKEN = Internal.createIndex("token", Session.SESSION, new OrderField[] { Session.SESSION.TOKEN }, true);
    }
}
