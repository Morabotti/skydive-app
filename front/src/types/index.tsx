import { LazyExoticComponent, FC, ElementType } from 'react'
import { PreloadableComponent } from 'react-lazy-with-preload'
import { SvgIconProps } from '@material-ui/core'
import { AuthRoles, RouteType } from '@enums'

export type ViewComponentProps = LazyExoticComponent<FC>
export type RouteComponent = ViewComponentProps | FC | PreloadableComponent<FC>

export interface LoginRequest {
  username: string,
  password: string
}

export interface User {
  username: string,
  role: AuthRoles
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
  email: string
}

export interface RegisterUserForm extends RegisterUser {
  rePassword: string,
  showPassword: boolean
}
