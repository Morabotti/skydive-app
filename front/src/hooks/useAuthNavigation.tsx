import { useMemo } from 'react'
import { matchPath, useLocation } from 'react-router'
import { authNavigation } from '@routes'
import { Route } from '@types'

interface AuthNavigationContext {
  currentRoute: Route | undefined
}

export const useAuthNavigation = (): AuthNavigationContext => {
  const { pathname } = useLocation()

  const currentRoute = useMemo(() => {
    const primaryRoutes = authNavigation.find(i => {
      const isMatch = matchPath(pathname, i.path)
      return isMatch?.isExact
    })

    return primaryRoutes
  }, [pathname])

  return {
    currentRoute
  }
}
