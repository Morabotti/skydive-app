import { Club, PaginationResult, User } from '@types'
import { QueryResult, usePaginatedQuery } from 'react-query'
import { Client } from '@enums'
import { getClubs, getUsers } from '@client'
import { usePagination } from '@hooks'

interface ConfigurationContext {
  users: QueryResult<PaginationResult<User>>,
  clubs: QueryResult<PaginationResult<Club>>
}

export const useConfiguration = (): ConfigurationContext => {
  const { limit, offset } = usePagination()
  const users = usePaginatedQuery([Client.GET_USERS, { limit, offset }], getUsers)
  const clubs = usePaginatedQuery([
    Client.GET_CLUBS,
    { limit, offset },
    { city: '', isPublic: null, search: '' }
  ], getClubs)

  return {
    users,
    clubs
  }
}
