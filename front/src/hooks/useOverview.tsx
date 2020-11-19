import { useMemo } from 'react'
import { ClubAccount, Participation } from '@types'
import { QueryResult, useQuery } from 'react-query'
import { Client } from '@enums'
import { getPersonalActivities, getPersonalClubs } from '@client'

interface OverviewContext {
  clubs: QueryResult<ClubAccount[]>,
  activities: QueryResult<Participation[]>,
  mainClub: ClubAccount | undefined
}

export const useOverview = (): OverviewContext => {
  const clubs = useQuery(Client.MY_CLUBS, getPersonalClubs)
  const activities = useQuery(Client.MY_ACTIVITIES, getPersonalActivities)

  const mainClub = useMemo(() => {
    if (!clubs.data) {
      return undefined
    }

    if (clubs.data.length === 1) {
      return clubs.data[0] || undefined
    }

    const active = clubs.data.filter(i => i.accepted)

    if (active.length === 1) {
      return active[0] || undefined
    }
  }, [clubs.data])

  return {
    clubs,
    activities,
    mainClub
  }
}
