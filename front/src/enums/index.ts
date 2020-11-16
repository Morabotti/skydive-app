export enum RouteType {
  AUTHED_ROUTED = 'auth-routed',
  AUTHED_PRIVATE = 'auth-private',
  PUBLIC = 'public'
}

export enum AuthRoles {
  ADMIN = 'admin',
  USER = 'user'
}

export enum LocalStorageKeys {
  TOKEN = 'token'
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
  MISC = 'misc'
}

export enum ActivityAccess {
  OPEN = 'open',
  CLUB_ONLY = 'club-only',
  INVITE = 'invite',
  PILOT_ONLY = 'pilot-only',
  USER_ONLY = 'user-only'
}

export enum ClubRole {
  CLUB = 'club',
  PILOT = 'pilot',
  USER = 'user'
}
