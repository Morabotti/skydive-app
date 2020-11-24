import { useCallback } from 'react'
import { useHistory, useLocation } from 'react-router-dom'
import { useCaching, usePagination } from '@hooks'

interface ControlsContext {
  limit: number,
  offset: number,
  onChangeLimit: (set: number) => void,
  onChangeOffset: (set: number) => void,
  onNextPage: () => void,
  onPrevPage: () => void
}

export const useControls = (): ControlsContext => {
  const { pathname, search } = useLocation()
  const { setCacheItem } = useCaching()
  const { limit, offset } = usePagination()
  const { replace } = useHistory()

  const updatePath = useCallback((newLimit: number, newOffset: number) => {
    const params = new URLSearchParams(search)

    const offsetParam = params.get('offset')
    const limitParam = params.get('limit')
    let changed = false
    if (parseInt(offsetParam || '0') !== newOffset) {
      params.set('offset', newOffset.toString())
      changed = true
    }

    if (parseInt(limitParam || '0') !== newLimit) {
      params.set('limit', newLimit.toString())
      setCacheItem('lastLimit', newLimit)

      changed = true
    }

    if (changed) {
      replace(`${pathname}?${params}`)
    }
  }, [pathname, replace, search, setCacheItem])

  const onChangeLimit = useCallback((set: number) => {
    updatePath(set, offset)
  }, [offset, updatePath])

  const onChangeOffset = useCallback((set: number) => {
    updatePath(limit, set)
  }, [limit, updatePath])

  const onNextPage = useCallback(() => {
    updatePath(limit, offset + limit)
  }, [offset, limit, updatePath])

  const onPrevPage = useCallback(() => {
    updatePath(limit, offset - limit < 0 ? 0 : offset - limit)
  }, [offset, limit, updatePath])

  return {
    limit,
    offset,
    onChangeLimit,
    onChangeOffset,
    onNextPage,
    onPrevPage
  }
}
