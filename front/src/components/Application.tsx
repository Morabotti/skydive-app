import React, { FC, Suspense, lazy } from 'react'
import { hot } from 'react-hot-loader'
import { BrowserRouter, Switch, Route } from 'react-router-dom'
import { publicRoutes, dashboardRoutes } from '@routes'
import { AuthProvider, DashboardProvider, StateContextProvider } from '@hooks'
import { QueryCache, ReactQueryCacheProvider } from 'react-query'

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

const queryCache = new QueryCache({
  defaultConfig: {
    queries: {
      refetchOnWindowFocus: false
    }
  }
})

const Application: FC = () => (
  <ApplicationTheme>
    <ReactQueryCacheProvider queryCache={queryCache}>
      <BrowserRouter>
        <AuthProvider>
          <Suspense fallback={<PrimaryLoader />}>
            <ApplicationDates>
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
            </ApplicationDates>
          </Suspense>
        </AuthProvider>
      </BrowserRouter>
    </ReactQueryCacheProvider>
  </ApplicationTheme>
)

export default hot(module)(Application)
