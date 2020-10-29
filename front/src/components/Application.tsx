import React, { FC, Suspense, lazy } from 'react'
import { hot } from 'react-hot-loader'
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom'
import { publicRoutes, portalRoutes } from '@routes'
import { AuthProvider, DashboardProvider } from '@hooks'

import {
  DashboardAuthLayer,
  PrimaryLoader,
  ApplicationTheme,
  PageSuspense,
  ViewLoader,
  ApplicationDates,
  SnackbarContainer
} from '@components/common'

const AuthNavigation = lazy(() => import('@components/navigation/AuthNavigation'))

const Application: FC = () => (
  <ApplicationTheme>
    <BrowserRouter>
      <AuthProvider>
        <Suspense fallback={<PrimaryLoader />}>
          <ApplicationDates>
            <Switch>
              <Route path='/dashboard'>
                <DashboardAuthLayer>
                  <SnackbarContainer>
                    <DashboardProvider>
                      <AuthNavigation>
                        <Suspense fallback={<PageSuspense />}>
                          {portalRoutes.map(({ path, access, component: Component }) => (
                            <Route exact key={path} path={path}>
                              <ViewLoader access={access}>
                                <Component />
                              </ViewLoader>
                            </Route>
                          ))}
                        </Suspense>
                      </AuthNavigation>
                    </DashboardProvider>
                  </SnackbarContainer>
                </DashboardAuthLayer>
              </Route>
              <Route path='/'>
                <Suspense fallback={<PageSuspense manualHeight />}>
                  {publicRoutes.map(({ component: Component, path }) => (
                    <Route key={path} exact path={path}>
                      <ViewLoader>
                        <Component />
                      </ViewLoader>
                    </Route>
                  ))}
                </Suspense>
                <Redirect to='/login' />
              </Route>
            </Switch>
          </ApplicationDates>
        </Suspense>
      </AuthProvider>
    </BrowserRouter>
  </ApplicationTheme>
)

export default hot(module)(Application)
