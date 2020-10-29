import { FC } from 'react'
import { Route, RouterRoute, RouteComponent } from '@types'
import { AuthRoles, RouteType } from '@enums'
import lazy from 'react-lazy-with-preload'

import {
  Speedometer,
  Account,
  Home
} from 'mdi-material-ui'

const LoginView = lazy(() => import('@components/login/LoginView'))
const OverviewView = lazy(() => import('@components/overview/OverviewView'))
const UserListView = lazy(() => import('@components/users/user-list/UserListView'))
const GlobalConfigurationView = lazy(() => import('@components/configuration/global-configuration/GlobalConfigurationView'))

export const routesTree: Route[] = [{
  access: [],
  type: RouteType.PUBLIC,
  component: LoginView,
  path: '/login'
}, {
  access: [AuthRoles.ADMIN],
  type: RouteType.AUTHED_ROUTED,
  component: OverviewView,
  path: '/dashboard',
  icon: Home,
  name: 'Dashboard overview'
}, {
  access: [AuthRoles.ADMIN],
  type: RouteType.AUTHED_ROUTED,
  path: '/dashboard/configuration',
  icon: Speedometer,
  component: GlobalConfigurationView,
  name: 'Configuration'
}, {
  access: [AuthRoles.ADMIN],
  type: RouteType.AUTHED_ROUTED,
  path: '/portal/users',
  icon: Account,
  name: 'Users',
  component: UserListView
}]

export const publicRoutes = routesTree
  .filter(i => i.type === RouteType.PUBLIC)
  .map(i => ({
    component: i.component as FC,
    path: i.path
  }))

export const portalRoutes: RouterRoute[] = routesTree
  .filter(i => i.type !== RouteType.PUBLIC)
  .map(i => i.navigation ? i.navigation.map(x => ({
    path: x.path,
    component: x.component,
    access: x.access
  })) : {
    path: i.path,
    component: i.component as RouteComponent,
    access: i.access
  })
  .flat()

export const authNavigation = routesTree.filter(i => i.type === RouteType.AUTHED_ROUTED)
