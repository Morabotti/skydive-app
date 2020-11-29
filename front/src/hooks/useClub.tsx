import { useCallback, useMemo } from 'react'
import { QueryResult, useQuery } from 'react-query'
import { Client, ClubRole, ClubTab } from '@enums'
import { getClubBySlug, getPersonalClubs } from '@client'
import { Club } from '@types'
import { useHistory, useLocation, useParams } from 'react-router-dom'

interface ClubContext {
  tab: ClubTab,
  club: QueryResult<Club>,
  isMember: boolean,
  isOwner: boolean,
  onChangeTab: (set: ClubTab) => void
}

export const useClub = (): ClubContext => {
  const { slug } = useParams<{ slug: string }>()
  const { search, pathname } = useLocation()
  const { push } = useHistory()

  const club = useQuery([Client.GET_CLUB_BY_ID, slug], getClubBySlug)
  const myClubs = useQuery(Client.MY_CLUBS, getPersonalClubs)

  const tab: ClubTab = useMemo(() => {
    const params = new URLSearchParams(search)
    const tab = params.get('tab')

    if (tab === null || tab === '') {
      return ClubTab.MAIN
    }

    return tab as ClubTab
  }, [search])

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

  const { isMember, isOwner } = useMemo(() => {
    const membership = myClubs.data?.find(i => i.club?.id === club.data?.id)

    return {
      isMember: membership !== undefined,
      isOwner: membership !== undefined
        && membership.accepted !== null
        && membership.role === ClubRole.CLUB
    }
  }, [myClubs, club])

  return {
    tab,
    club,
    onChangeTab,
    isMember,
    isOwner
  }
}
