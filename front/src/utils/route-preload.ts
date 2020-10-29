import { matchPath } from 'react-router-dom'
import { routesTree } from '@routes'

export const routePreload = (url: string) => {
  for (const route of routesTree) {
    const isMatch = matchPath(url, route.path)

    if (!isMatch) {
      continue
    }

    if (isMatch.isExact) {
      if (route.component) {
        if ('preload' in route.component) {
          route.component.preload()
        }
        return
      }
    }

    if (!route.navigation) {
      continue
    }

    for (const sub of route.navigation) {
      const match = matchPath(url, { exact: true, path: sub.path })

      if (!match) {
        continue
      }

      if (match.isExact) {
        if (sub.component && 'preload' in sub.component) {
          sub.component.preload()
        }
        return
      }
    }
  }
}
