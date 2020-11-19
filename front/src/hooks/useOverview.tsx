import { ClubAccount, Participation } from '@types'
import { QueryResult, useQuery } from 'react-query'
import { Client } from '@enums'
import { getPersonalActivities, getPersonalClubs } from '@client'

interface OverviewContext {
  clubs: QueryResult<ClubAccount[]>,
  activities: QueryResult<Participation[]>
}

export const useOverview = (): OverviewContext => {
  const clubs = useQuery(Client.MY_CLUBS, getPersonalClubs)
  const activities = useQuery(Client.MY_ACTIVITIES, getPersonalActivities)

  return {
    clubs,
    activities
  }
}
