export enum Client {
  MY_CLUBS,
  MY_ACTIVITIES,
  CREATE_CLUB,
  UPDATE_CLUB,
  GET_CLUBS,
  GET_CLUB_BY_ID,
  GET_CLUB_BY_SLUG,
  DELETE_CLUB,
  GET_CLUB_ACTIVITY,
  GET_CLUB_PLANES,
  GET_CLUB_PLANE_BY_ID,
  UPDATE_CLUB_PLANE,
  DELETE_PLANE,
  CREATE_PLANE,
  GET_CLUB_MEMBERS,
  GET_CLUB_MEMBER_BY_ID,
  DELETE_CLUB_MEMBER,
  ADD_CLUB_MEMBER,
  REQUEST_CLUB_MEMBER,
  ACCEPT_CLUB_MEMBER,
  DECLINE_CLUB_MEMBER,
  GET_ACTIVITES,
  GET_ACTIVITY_BY_ID,
  GET_ACTIVITY_BY_TOKEN,
  CREATE_ACTIVITY,
  UPDATE_ACTIVITIY,
  DELETE_ACTIVITY,
  GET_ACTIVITY_PARTICIPATION,
  PARTICIPATE_AS_USER,
  PARTICIPATE_AS_PILOT,
  REMOVE_PARTICIPATION_AS_USER,
  REMOVE_PARTICIPATION_AS_PILOT,
  GET_USERS,
  GET_USER_BY_ID,
  UPDATE_USER,
  DELETE_USER,
  GET_USER_ACTIVITY,
  GET_USER_CLUBS,
  GET_USER_JUMP
}

export enum RouteType {
  AUTHED_ROUTED = 'auth-routed',
  AUTHED_PRIVATE = 'auth-private',
  PUBLIC = 'public'
}

export enum AuthRoles {
  ADMIN = 'admin',
  USER = 'user',
  UNSET = 'unset' // Used only in filter views, not in actual api
}

export enum LocalStorageKeys {
  TOKEN = 'token',
  APPLICATION_CACHE = 'skydive-cache'
}

export enum NotificationType {
  WARNING = 'warning',
  ERROR = 'error',
  SUCCESS = 'success',
  INFO = 'info',
  DEFAULT = 'default'
}

export enum ActivityType {
  JUMP = 'jump',
  MISC = 'misc',
  UNSET = 'unset' // Used only in filter views, not in actual api
}

export enum ActivityAccess {
  OPEN = 'open',
  INVITE = 'invite',
  UNSET = 'unset' // Used only in filter views, not in actual api
}

export enum ClubRole {
  CLUB = 'club',
  PILOT = 'pilot',
  USER = 'user',
  UNSET = 'unset' // Used only in filter views, not in actual api
}

export enum ParticipationRole {
  USER = 'user',
  PILOT = 'pilot'
}

export enum ClubTab {
  MAIN = '',
  USERS = 'users',
  ACTIVITY = 'activites',
  CONFIG = 'configuration'
}
