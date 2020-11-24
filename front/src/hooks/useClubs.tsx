import { useCallback, useState } from 'react'
import { useDebounce, usePagination } from '@hooks'
import { QueryResult, usePaginatedQuery } from 'react-query'
import { Client } from '@enums'
import { getClubs } from '@client'
import { Club, PaginationResult } from '@types'

interface ClubsContext {
  isList: boolean,
  clubs: QueryResult<PaginationResult<Club>>,
  search: string,
  city: string,
  isPublic: boolean | null,
  toggleList: (set: boolean) => () => void,
  setSearch: (set: string) => void,
  setCity: (set: string) => void,
  toggleIsPublic: () => void
}

export const useClubs = (): ClubsContext => {
  const { offset, limit } = usePagination()
  const [isList, setIsList] = useState(true)
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
  }, [setIsList])

  const toggleIsPublic = useCallback(() => {
    setIsPublic(prev => prev === null ? true : null)
  }, [setIsPublic])

  return {
    isList,
    toggleList,
    clubs,
    city,
    search,
    isPublic,
    toggleIsPublic,
    setCity,
    setSearch
  }
}
