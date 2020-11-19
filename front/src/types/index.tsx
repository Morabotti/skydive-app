import { LazyExoticComponent, FC, ElementType } from 'react'
import { PreloadableComponent } from 'react-lazy-with-preload'
import { SvgIconProps } from '@material-ui/core'

import {
  ActivityAccess,
  ActivityType,
  AuthRoles,
  ClubRole,
  RouteType,
  ParticipationRole
} from '@enums'

export type ViewComponentProps = LazyExoticComponent<FC>
export type RouteComponent = ViewComponentProps | FC | PreloadableComponent<FC>

export interface LoginRequest {
  username: string,
  password: string
}

export interface PaginationResult<T> {
  result: T[],
  length: number
}

export interface PaginationQuery {
  limit: number,
  offset: number
}

export interface ClubQuery {
  city: string,
  isPublic: boolean
}

export interface User {
  id: number,
  username: string,
  role: AuthRoles,
  profile: null | UserProfile
}

export interface UserProfile {
  id: number,
  firstName: string,
  lastName: string,
  address: string,
  zipCode: string,
  phone: string,
  deletedAt: null | string
}

export interface AuthUser {
  token: string,
  user: User
}

export interface RequestContext {
  loading: boolean,
  error: boolean,
  setRequest: (loading: boolean, error?: boolean) => void
}

export interface Route {
  path: string,
  component?: RouteComponent,
  access: AuthRoles[],
  type: RouteType,
  name?: string,
  icon?: ElementType<SvgIconProps>,
  navigation?: PortalSubRoute[]
}

export interface PortalSubRoute {
  path: string,
  name: string,
  component: RouteComponent,
  access: AuthRoles[]
}

export interface RouterRoute {
  path: string,
  component: RouteComponent,
  access: AuthRoles[]
}

export interface PortalRoute {
  path: string,
  component: RouteComponent,
  access: AuthRoles[],
  type: RouteType,
  section: string | null,
  name: string,
  icon: ElementType<SvgIconProps>,
  subRoutes?: PortalSubRoute[]
}

export interface RegisterUser {
  firstName: string,
  lastName: string,
  address: string,
  city: string,
  zipCode: string,
  phone: string,
  password: string,
  username: string
}

export interface RegisterUserForm extends RegisterUser {
  rePassword: string,
  showPassword: boolean
}

export interface ClubRequest {
  name: string,
  slug: string,
  description: string,
  isPublic: boolean,
  address: string,
  city: string,
  zipCode: string,
  phone: string
}

export interface ClubProfile {
  id: number,
  description: string,
  address: string,
  zipCode: string,
  city: string,
  phone: string,
  deletedAt: null | string
}

export interface Club {
  id: number,
  name: string,
  slug: string,
  isPublic: boolean,
  clubProfile: ClubProfile,
  deletedAt: null | string
}

export interface Activity {
  id: number,
  title: string,
  description: string,
  type: ActivityType,
  access: ActivityAccess,
  visible: boolean,
  token: string,
  startDate: string,
  endDate: string | null,
  createdAt: string,
  deletedAt: null | string,
  club: Club | null
}

export interface UpdateActivity {
  title: string,
  description: string,
  type: ActivityType,
  access: ActivityAccess,
  visibility: boolean,
  startDate: string,
  endDate: string,
  club: Club
}

export interface Plane {
  id: number,
  licenseNumber: string,
  active: boolean,
  seats: number,
  deletedAt: null | string
}

export type UpdatePlane = Omit<Plane, 'deletedAt'>

export interface ClubMemberRequest {
  role: ClubRole
}

export interface Participation {
  id: number,
  active: boolean,
  role: ParticipationRole,
  account: null | User,
  activity: null | Activity,
  createdAt: string,
  deletedAt: string | null
}

export interface PilotParticipation extends Participation {
  plane: Plane | null
}

export interface ClubAccount {
  id: number,
  role: ClubRole,
  accepted: string | null,
  createdAt: string,
  club: Club | null,
  account: Account | null
}
