import { useCallback } from 'react'
import { useHistory } from 'react-router'
import { routePreload } from '@utils/route-preload'

interface ApplicationNavigationContext {
  preloadRoute: (url: string) => void,
  onRoutePreload: (url: string) => () => void,
  onNavigation: (to: string) => (e?: React.MouseEvent) => void
}

export const useApplicationNavigation = (): ApplicationNavigationContext => {
  const { push } = useHistory()

  const onRoutePreload = useCallback((url: string) => () => {
    routePreload(url)
  }, [])

  const preloadRoute = useCallback((url: string) => {
    routePreload(url)
  }, [])

  const onNavigation = useCallback(
    (to: string) => (e?: React.MouseEvent) => {
      e?.preventDefault()
      push(to)
      window.scrollTo(0, 0)
    },
    [push]
  )

  return {
    preloadRoute,
    onNavigation,
    onRoutePreload
  }
}
