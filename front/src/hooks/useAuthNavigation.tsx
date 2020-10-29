import { useState, useCallback, useMemo } from 'react'
import { useHistory, matchPath, useLocation } from 'react-router'
import { useTheme, useMediaQuery } from '@material-ui/core'
import { routePreload } from '@utils/route-preload'
import { authNavigation } from '@routes'
import { Route } from '@types'

interface AuthNavigationContext {
  expanded: boolean,
  openNavigations: string[],
  minimized: boolean,
  settingsOpen: boolean,
  currentRoute: Route | undefined,
  toggleExpanded: () => void,
  toggleSettingsDialog: (set: boolean) => () => void,
  toggleMinimize: () => void,
  onNavigation: (url: string) => () => void,
  onRoutePreload: (url: string) => () => void,
  onExpandNavigation: (category: string) => () => void
}

export const useAuthNavigation = (): AuthNavigationContext => {
  const { push } = useHistory()
  const { pathname } = useLocation()
  const theme = useTheme()
  const isMobile = useMediaQuery(theme.breakpoints.down('xs'))
  const [expanded, setExpanded] = useState(false)
  const [minimized, setMinimized] = useState(false)
  const [settingsOpen, setSettingsOpen] = useState(false)
  const [openNavigations, setOpenNavigations] = useState<string[]>([])

  const onExpandNavigation = useCallback((category: string) => () => {
    setOpenNavigations(prev => prev.includes(category)
      ? prev.filter(i => i !== category)
      : [...prev, category]
    )
  }, [setOpenNavigations])

  const toggleExpanded = useCallback(() => {
    setExpanded(prev => !prev)
  }, [setExpanded])

  const toggleSettingsDialog = useCallback((set: boolean) => () => {
    setSettingsOpen(set)
  }, [setSettingsOpen])

  const toggleMinimize = useCallback(() => {
    setMinimized(prev => !prev)
  }, [setMinimized])

  const onNavigation = useCallback((url: string) => () => {
    push(url)
    if (isMobile) {
      setExpanded(false)
    }
  }, [push, isMobile, setExpanded])

  const onRoutePreload = useCallback((url: string) => () => {
    routePreload(url)
  }, [])

  const currentRoute = useMemo(() => {
    const primaryRoutes = authNavigation.find(i => {
      const isMatch = matchPath(pathname, i.path)
      return isMatch?.isExact || i.navigation?.find(x => matchPath(pathname, x.path))
    })

    return primaryRoutes
  }, [pathname])

  return {
    expanded,
    minimized,
    openNavigations,
    settingsOpen,
    currentRoute,
    toggleExpanded,
    toggleMinimize,
    toggleSettingsDialog,
    onNavigation,
    onRoutePreload,
    onExpandNavigation
  }
}
