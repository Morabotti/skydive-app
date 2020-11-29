import { useCallback, useState } from 'react'
import { useDebounce, usePagination, useCaching } from '@hooks'
import { QueryResult, usePaginatedQuery } from 'react-query'
import { Client } from '@enums'
import { getClubs } from '@client'
import { Club, PaginationResult } from '@types'

interface ClubsContext {
  isList: boolean,
  extended: boolean,
  clubs: QueryResult<PaginationResult<Club>>,
  search: string,
  city: string,
  isPublic: boolean | null,
  toggleList: (set: boolean) => () => void,
  toggleExtended: (set: boolean) => () => void,
  setSearch: (set: string) => void,
  setCity: (set: string) => void,
  setIsPublic: (set: null | boolean) => void,
  onResetFilters: () => void
}

export const useClubs = (): ClubsContext => {
  const { offset, limit } = usePagination()
  const { getCacheItem, setCacheItem } = useCaching()

  const [isList, setIsList] = useState(getCacheItem<boolean>('clubsInList'))
  const [extended, setExtended] = useState(getCacheItem<boolean>('extendedLists'))
  const [search, setSearch] = useState('')
  const [city, setCity] = useState('')
  const [isPublic, setIsPublic] = useState<null | boolean>(null)

  const debouncedSearch = useDebounce(search, 300)
  const debouncedCity = useDebounce(city, 300)

  const clubs = usePaginatedQuery(
    [Client.GET_CLUBS, {
      limit,
      offset
    }, {
      search: debouncedSearch,
      city: debouncedCity,
      isPublic: isPublic === null ? null : !isPublic
    }],
    getClubs
  )

  const toggleList = useCallback((set: boolean) => () => {
    setIsList(set)
    setCacheItem('clubsInList', set)
  }, [setIsList, setCacheItem])

  const toggleExtended = useCallback((set: boolean) => () => {
    setExtended(set)
    setCacheItem('extendedLists', set)
  }, [setExtended, setCacheItem])

  const onResetFilters = useCallback(() => {
    setSearch('')
    setCity('')
    setIsPublic(null)
  }, [])

  return {
    isList,
    extended,
    toggleList,
    toggleExtended,
    clubs,
    city,
    search,
    isPublic,
    setIsPublic,
    setCity,
    setSearch,
    onResetFilters
  }
}
