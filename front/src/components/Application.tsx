import React, { FC, Suspense, lazy } from 'react'
import { hot } from 'react-hot-loader'
import { BrowserRouter, Switch, Route } from 'react-router-dom'
import { publicRoutes, dashboardRoutes } from '@routes'
import { AuthProvider, DashboardProvider, StateContextProvider } from '@hooks'
// import { ReactQueryDevtools } from 'react-query-devtools'

import {
  DashboardAuthLayer,
  PrimaryLoader,
  PageSuspense,
  ViewLoader,
  SnackbarContainer,
  ApplicationProviders
} from '@components/common'

const AuthNavigation = lazy(() => import('@components/navigation/AuthNavigation'))

const Application: FC = () => (
  <ApplicationProviders>
    <BrowserRouter>
      <AuthProvider>
        <Suspense fallback={<PrimaryLoader />}>
          <Switch>
            <Route path='/dashboard'>
              <DashboardAuthLayer>
                <SnackbarContainer>
                  <DashboardProvider>
                    <StateContextProvider>
                      <AuthNavigation>
                        <Suspense fallback={<PageSuspense />}>
                          {dashboardRoutes.map(({
                            path,
                            access,
                            component: Component
                          }) => (
                            <Route exact key={path} path={path}>
                              <ViewLoader access={access}>
                                <Component />
                              </ViewLoader>
                            </Route>
                          ))}
                        </Suspense>
                      </AuthNavigation>
                    </StateContextProvider>
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
            </Route>
          </Switch>
        </Suspense>
      </AuthProvider>
    </BrowserRouter>
    {/* <ReactQueryDevtools initialIsOpen={false} /> */}
  </ApplicationProviders>
)

export default hot(module)(Application)
