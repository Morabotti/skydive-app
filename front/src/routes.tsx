import { FC } from 'react'
import { Route, RouterRoute, RouteComponent } from '@types'
import { AuthRoles, RouteType } from '@enums'
import lazy from 'react-lazy-with-preload'

import {
  AccountGroup,
  Cogs,
  Home,
  MapMarkerRadius
} from 'mdi-material-ui'

const LoginView = lazy(() => import('@components/auth/LoginView'))
const RegisterView = lazy(() => import('@components/auth/RegisterView'))
const OverviewView = lazy(() => import('@components/overview/OverviewView'))
const ActivityView = lazy(() => import('@components/activity/ActivityView'))
const ActivitiesView = lazy(() => import('@components/activities/ActivitiesView'))
const ClubsView = lazy(() => import('@components/clubs/ClubsView'))
const ClubView = lazy(() => import('@components/club/ClubView'))
const ConfigurationView = lazy(() => import('@components/configuration/ConfigurationView'))
const CreateClubView = lazy(() => import('@components/configuration/create-club/CreateClubView'))

export const routesTree: Route[] = [{
  access: [],
  type: RouteType.PUBLIC,
  component: LoginView,
  path: '/login'
}, {
  access: [],
  type: RouteType.PUBLIC,
  component: RegisterView,
  path: '/register'
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_ROUTED,
  component: OverviewView,
  path: '/dashboard',
  name: 'Overview',
  icon: Home
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_ROUTED,
  component: ClubsView,
  path: '/dashboard/clubs',
  name: 'Clubs',
  icon: AccountGroup
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_PRIVATE,
  component: ClubView,
  path: '/dashboard/club/:slug',
  name: 'Club'
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_ROUTED,
  component: ActivitiesView,
  path: '/dashboard/activities',
  name: 'Activities',
  icon: MapMarkerRadius
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_PRIVATE,
  component: ActivityView,
  path: '/dashboard/activity/:token',
  name: 'Activity'
}, {
  access: [AuthRoles.ADMIN],
  type: RouteType.AUTHED_ROUTED,
  path: '/dashboard/configuration',
  component: ConfigurationView,
  name: 'Configuration',
  icon: Cogs
}, {
  access: [AuthRoles.ADMIN],
  type: RouteType.AUTHED_PRIVATE,
  path: '/dashboard/configuration/club',
  component: CreateClubView,
  name: 'Create Club'
}]

export const publicRoutes = routesTree
  .filter(i => i.type === RouteType.PUBLIC)
  .map(i => ({
    component: i.component as FC,
    path: i.path
  }))

export const dashboardRoutes: RouterRoute[] = routesTree
  .filter(i => i.type !== RouteType.PUBLIC)
  .map(i => ({
    path: i.path,
    component: i.component as RouteComponent,
    access: i.access
  }))

export const authNavigation = routesTree.filter(i => i.type === RouteType.AUTHED_ROUTED)
