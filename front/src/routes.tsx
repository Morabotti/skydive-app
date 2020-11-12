import { FC } from 'react'
import { Route, RouterRoute, RouteComponent } from '@types'
import { AuthRoles, RouteType } from '@enums'
import lazy from 'react-lazy-with-preload'

import {
  Speedometer,
  Home
} from 'mdi-material-ui'

const LoginView = lazy(() => import('@components/auth/LoginView'))
const RegisterView = lazy(() => import('@components/auth/RegisterView'))
const OverviewView = lazy(() => import('@components/overview/OverviewView'))
const ConfigurationView = lazy(() => import('@components/configuration/ConfigurationView'))

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
  icon: Home,
  name: 'Dashboard overview'
}, {
  access: [AuthRoles.ADMIN, AuthRoles.USER],
  type: RouteType.AUTHED_ROUTED,
  path: '/dashboard/configuration',
  icon: Speedometer,
  component: ConfigurationView,
  name: 'Configuration'
}]

export const publicRoutes = routesTree
  .filter(i => i.type === RouteType.PUBLIC)
  .map(i => ({
    component: i.component as FC,
    path: i.path
  }))

export const dashboardRoutes: RouterRoute[] = routesTree
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
