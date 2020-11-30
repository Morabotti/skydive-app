import { useCallback, useMemo } from 'react'
import { QueryCache, QueryResult, useMutation, useQuery, useQueryCache } from 'react-query'
import { Client, ClubRole, ClubTab, NotificationType } from '@enums'
import { getClubBySlug, getPersonalClubs, leaveClub, requestToJoinClub } from '@client'
import { Club, ClubAccount } from '@types'
import { useHistory, useLocation, useParams } from 'react-router-dom'
import { useDashboard } from '@hooks'

interface ClubContext {
  tab: ClubTab,
  club: QueryResult<Club>,
  myClubs: QueryResult<ClubAccount[]>,
  isMember: boolean,
  isOwner: boolean,
  accepted: boolean,
  clubRole: ClubRole | undefined,
  onChangeTab: (set: ClubTab) => void,
  onJoin: () => void,
  onLeave: () => void
}

const invalidatePersonalClubs = (
  queryCache: QueryCache,
  includeActivites: boolean
) => {
  queryCache.invalidateQueries(Client.MY_CLUBS)
  queryCache.invalidateQueries(Client.GET_CLUB_MEMBERS)

  if (includeActivites) {
    queryCache.invalidateQueries(Client.MY_ACTIVITIES)
  }
}

export const useClub = (): ClubContext => {
  const { slug } = useParams<{ slug: string }>()
  const { createNotification, setLoading } = useDashboard()
  const { search, pathname } = useLocation()
  const { push } = useHistory()
  const queryCache = useQueryCache()

  const club = useQuery([Client.GET_CLUB_BY_ID, slug], getClubBySlug)
  const myClubs = useQuery(Client.MY_CLUBS, getPersonalClubs)

  const [joinRequest] = useMutation(requestToJoinClub, {
    onSuccess: () => invalidatePersonalClubs(queryCache, false)
  })

  const [leaveRequest] = useMutation(leaveClub, {
    onSuccess: () => invalidatePersonalClubs(queryCache, true)
  })

  const tab: ClubTab = useMemo(() => {
    const params = new URLSearchParams(search)
    const tab = params.get('tab')

    if (tab === null || tab === '') {
      return ClubTab.MAIN
    }

    return tab as ClubTab
  }, [search])

  const { isMember, isOwner, clubRole, accepted } = useMemo(() => {
    const membership = myClubs.data?.find(i => i.club?.id === club.data?.id)

    return {
      isMember: membership !== undefined,
      isOwner: membership !== undefined
        && membership.accepted !== null
        && membership.role === ClubRole.CLUB,
      clubRole: membership?.role,
      accepted: (membership?.accepted || null) !== null
    }
  }, [myClubs, club])

  const onChangeTab = useCallback((set: ClubTab) => {
    const params = new URLSearchParams(search)
    const oldTab = params.get('tab')

    if ((oldTab === null || oldTab === '') && set === ClubTab.MAIN) {
      return
    }

    if (oldTab === set) {
      return
    }

    if (set === ClubTab.MAIN) {
      params.delete('tab')
    }
    else {
      params.set('tab', set)
    }

    push(`${pathname}?${params}`)
  }, [pathname, push, search])

  const onJoin = useCallback(async () => {
    if (!club.data) {
      return
    }

    setLoading(true)

    try {
      await joinRequest([club.data.id, { role: ClubRole.USER }])
      createNotification('Successfully created join request', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to create a request to join a club. Try again later.', NotificationType.ERROR)
    }
    setLoading(false)
  }, [joinRequest, club, setLoading, createNotification])

  const onLeave = useCallback(async () => {
    if (!club.data) {
      return
    }

    setLoading(true)

    try {
      await leaveRequest(club.data.id)
      createNotification('Successfully leaved a club', NotificationType.INFO)
    }
    catch (e) {
      createNotification('Failed to leave club', NotificationType.ERROR)
    }
    setLoading(false)
  }, [setLoading, club, leaveRequest, createNotification])

  return {
    tab,
    club,
    myClubs,
    onChangeTab,
    isMember,
    isOwner,
    accepted,
    clubRole,
    onLeave,
    onJoin
  }
}
