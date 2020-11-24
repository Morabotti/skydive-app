import { useCallback, useMemo, useState } from 'react'
import { matchPath, useLocation } from 'react-router'
import { authNavigation } from '@routes'
import { Route } from '@types'

interface AuthNavigationContext {
  settings: boolean,
  currentRoute: Route | undefined,
  toggleSettings: (set: boolean) => () => void
}

export const useAuthNavigation = (): AuthNavigationContext => {
  const { pathname } = useLocation()
  const [settings, setSettings] = useState(false)

  const currentRoute = useMemo(() => {
    const primaryRoutes = authNavigation.find(i => {
      const isMatch = matchPath(pathname, i.path)
      return isMatch?.isExact
    })

    return primaryRoutes
  }, [pathname])

  const toggleSettings = useCallback((set: boolean) => () => {
    setSettings(set)
  }, [setSettings])

  return {
    settings,
    currentRoute,
    toggleSettings
  }
}
