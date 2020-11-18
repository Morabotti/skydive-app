import { useCallback, useEffect } from 'react'
import { useGlobalState } from '@hooks'
import { Activity, Club } from '@types'

interface OverviewContext {
  myClubs: Club[] | null,
  myActivities: Activity[] | null
}

export const useOverview = (): OverviewContext => {
  const { myActivities, myClubs } = useGlobalState()

  const fetchInitials = useCallback(() => {
    if (myClubs === null || myActivities === null) {
      return
    }

    console.log(myClubs, myActivities)
  }, [myActivities, myClubs])

  useEffect(() => {
    fetchInitials()
  }, [fetchInitials])

  return {
    myActivities,
    myClubs
  }
}
