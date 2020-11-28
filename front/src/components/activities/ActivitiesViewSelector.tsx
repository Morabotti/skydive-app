import React, { useCallback } from 'react'
import { ActivityListView } from '@components/activities'
import { Activity } from '@types'
import { AuthRoles, Client } from '@enums'
import { useQueryCache } from 'react-query'
import { useHistory } from 'react-router-dom'
import { useApplicationNavigation } from '@hooks'
import { CenterMessage } from '@components/common'
import { TableCancel } from 'mdi-material-ui'

interface Props {
  isList: boolean,
  loading: boolean,
  activities: Activity[],
  role?: AuthRoles,
  onResetFilters: () => void
}

export const ActivitiesViewSelector = ({
  isList,
  activities,
  loading,
  role,
  onResetFilters
}: Props) => {
  const queryCache = useQueryCache()
  const { onRoutePreload } = useApplicationNavigation()
  const { push } = useHistory()

  const onActivitySelect = useCallback((set: Activity) => () => {
    queryCache.setQueryData([Client.GET_ACTIVITY_BY_TOKEN, set.token], set)
    push(`/dashboard/activity/${set.token}`)
  }, [push, queryCache])

  if (activities.length === 0 && !loading) {
    return (
      <CenterMessage
        icon={TableCancel}
        text='No activities found with selected filters.'
        onClick={onResetFilters}
        buttonText='Reset filters'
      />
    )
  }

  if (isList) {
    return (
      <ActivityListView
        loading={loading}
        activities={activities}
        role={role}
        onRoutePreload={onRoutePreload}
        onActivitySelect={onActivitySelect}
      />
    )
  }

  return (
    <div />
  )
}
