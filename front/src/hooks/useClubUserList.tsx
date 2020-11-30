import { useCallback, useState } from 'react'
import { QueryCache, QueryResult, useMutation, usePaginatedQuery, useQueryCache } from 'react-query'
import { Client, ClubRole, NotificationType } from '@enums'
import { ClubAccount, PaginationResult } from '@types'
import { useDashboard, usePagination, useDebounce } from '@hooks'

import {
  acceptClubMemberRequest,
  declineClubMemberRequest,
  getClubMembers,
  deleteClubMember
} from '@client'

const invalidateClubMembers = (
  queryCache: QueryCache,
  id: number,
  memberId: number | null = null
) => {
  queryCache.invalidateQueries([Client.GET_CLUB_MEMBERS, id])
  if (memberId !== null) {
    queryCache.invalidateQueries([Client.GET_CLUB_MEMBER_BY_ID, id, memberId])
  }
}

interface ClubUsersListContext {
  members: QueryResult<PaginationResult<ClubAccount>>,
  role: ClubRole,
  search: string,
  accepted: boolean,
  toggleAccepted: () => void,
  setSearch: (set: string) => void,
  setRole: (set: ClubRole) => void,
  onDecline: (set: ClubAccount) => () => void,
  onAccept: (set: ClubAccount) => () => void,
  onRemove: (set: ClubAccount) => () => void,
  onResetFilters: () => void
}

export const useClubUserList = (id: number): ClubUsersListContext => {
  const { createNotification, setLoading } = useDashboard()
  const queryCache = useQueryCache()

  const { limit, offset } = usePagination()

  const [search, setSearch] = useState('')
  const [role, setRole] = useState<ClubRole>(ClubRole.UNSET)
  const [accepted, setAccepted] = useState(true)

  const debouncedSearch = useDebounce(search, 300)

  const members = usePaginatedQuery(
    [Client.GET_CLUB_MEMBERS, id, {
      limit,
      offset
    }, {
      accepted,
      search: debouncedSearch,
      role: role === ClubRole.UNSET ? null : role
    }],
    getClubMembers
  )

  const [ acceptMember ] = useMutation(acceptClubMemberRequest, {
    onSuccess: (d: ClubAccount, v) => invalidateClubMembers(queryCache, v[0], v[1])
  })

  const [ declineMember ] = useMutation(declineClubMemberRequest, {
    onSuccess: (d: Response, v) => invalidateClubMembers(queryCache, v[0], v[1])
  })

  const [ removeMember ] = useMutation(deleteClubMember, {
    onSuccess: (d: Response, v) => invalidateClubMembers(queryCache, v[0], v[1])
  })

  const toggleAccepted = useCallback(() => {
    setAccepted(prev => !prev)
  }, [setAccepted])

  const onDecline = useCallback((set: ClubAccount) => async () => {
    if (id === undefined || set.account === null) {
      return
    }

    setLoading(true)
    try {
      await declineMember([id, set.account.id])
      createNotification('Successfully declined a request', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to declined request', NotificationType.ERROR)
    }
    setLoading(false)
  }, [setLoading, createNotification, declineMember, id])

  const onAccept = useCallback((set: ClubAccount) => async () => {
    if (id === undefined || set.account === null) {
      return
    }

    setLoading(true)
    try {
      await acceptMember([id, set.account.id])
      createNotification('Successfully accepted a member', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to accept member', NotificationType.ERROR)
    }
    setLoading(false)
  }, [setLoading, createNotification, acceptMember, id])

  const onRemove = useCallback((set: ClubAccount) => async () => {
    if (id === undefined || set.account === null) {
      return
    }

    setLoading(true)
    try {
      await removeMember([id, set.account.id])
      createNotification('Successfully removed a member', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to remove member', NotificationType.ERROR)
    }
    setLoading(false)
  }, [setLoading, createNotification, removeMember, id])

  const onResetFilters = useCallback(() => {
    setSearch('')
    setRole(ClubRole.UNSET)
    setAccepted(true)
  }, [])

  return {
    members,
    search,
    accepted,
    role,
    toggleAccepted,
    setRole,
    setSearch,
    onDecline,
    onAccept,
    onRemove,
    onResetFilters
  }
}
