import { QueryResult, useQuery } from 'react-query'
import { Client } from '@enums'
import { getActivityByToken } from '@client'
import { Activity } from '@types'
import { useParams } from 'react-router-dom'

interface ActivityContext {
  activity: QueryResult<Activity>
}

export const useActivity = (): ActivityContext => {
  const { token } = useParams<{ token: string }>()

  const activity = useQuery([Client.GET_ACTIVITY_BY_TOKEN, token], getActivityByToken)

  return {
    activity
  }
}
