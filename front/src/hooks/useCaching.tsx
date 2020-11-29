import { useState, useCallback } from 'react'
import { LocalStorageKeys } from '@enums'

interface ApplicationCache {
  lastUsername: string,
  lastLimit: number,
  usersInList: boolean,
  clubsInList: boolean,
  activitiesInList: boolean,
  showLanderCalendar: boolean,
  extendedLists: boolean
}

const defaultCache: ApplicationCache = ({
  lastUsername: '',
  lastLimit: 20,
  usersInList: true,
  clubsInList: true,
  activitiesInList: true,
  showLanderCalendar: true,
  extendedLists: true
})

const getInitialApplicationCache = (): ApplicationCache => {
  const cacheString = localStorage.getItem(
    LocalStorageKeys.APPLICATION_CACHE
  )

  if (cacheString === null) {
    localStorage.setItem(
      LocalStorageKeys.APPLICATION_CACHE,
      JSON.stringify(defaultCache)
    )

    return defaultCache
  }

  return JSON.parse(cacheString) as ApplicationCache
}

interface CachingContext {
  getCacheItem: <T>(key: keyof ApplicationCache) => T,
  setCacheItem: <T>(key: keyof ApplicationCache, value: T) => void,
  removeCacheItem: (key: keyof ApplicationCache) => void
}

export const useCaching = (): CachingContext => {
  const [cache, setCache] = useState<ApplicationCache>(getInitialApplicationCache)

  const getCacheItem = useCallback(<T extends {}>(key: keyof ApplicationCache): T => {
    if (cache[key] === undefined) {
      return (defaultCache[key] as unknown as T)
    }
    else {
      return (cache[key] as unknown as T)
    }
  }, [cache])

  const setCacheItem = useCallback((key: keyof ApplicationCache, value: unknown) => {
    const newCache = { ...cache, [key]: value as string }
    localStorage.setItem(LocalStorageKeys.APPLICATION_CACHE, JSON.stringify(newCache))
    setCache(newCache)
  }, [cache, setCache])

  const removeCacheItem = useCallback((key: keyof ApplicationCache) => {
    const newCache = { ...cache }
    // eslint-disable-next-line @typescript-eslint/ban-ts-ignore
    // @ts-ignore
    delete newCache[key]

    localStorage.setItem(LocalStorageKeys.APPLICATION_CACHE, JSON.stringify(newCache))
    setCache(newCache)
  }, [cache, setCache])

  return {
    getCacheItem,
    setCacheItem,
    removeCacheItem
  }
}
