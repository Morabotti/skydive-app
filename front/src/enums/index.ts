export enum RouteType {
  AUTHED_ROUTED = 'auth-routed',
  AUTHED_PRIVATE = 'auth-private',
  PUBLIC = 'public'
}

export enum AuthRoles {
  ADMIN = 'admin',
  CLUB = 'club',
  PILOT = 'pilot',
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
