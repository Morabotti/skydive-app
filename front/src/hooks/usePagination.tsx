import { useMemo } from 'react'
import { useLocation } from 'react-router-dom'
import { useCaching } from '@hooks'

interface PaginationContext {
  offset: number,
  limit: number
}

const getLimitFromUrl = (search: string): number | undefined => {
  const params = new URLSearchParams(search)

  const limitParam = params.get('limit')
  if (limitParam && !isNaN(Number(limitParam))) {
    return parseInt(limitParam)
  }

  return undefined
}

const getOffsetFromUrl = (search: string): number | undefined => {
  const params = new URLSearchParams(search)

  const offsetParam = params.get('offset')
  if (offsetParam && !isNaN(Number(offsetParam))) {
    return parseInt(offsetParam)
  }

  return undefined
}

export const usePagination = (): PaginationContext => {
  const { search } = useLocation()
  const { getCacheItem } = useCaching()

  const { offset, limit } = useMemo(() => {
    return {
      limit: getCacheItem('lastLimit') === 20
        ? getLimitFromUrl(search) || 20
        : 20,
      offset: getOffsetFromUrl(search) || 0
    }
  }, [search, getCacheItem])

  return {
    offset,
    limit
  }
}
