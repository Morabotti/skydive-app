import React, { useCallback } from 'react'
import { ClubListView } from '@components/clubs'
import { Club } from '@types'
import { AuthRoles, Client } from '@enums'
import { useQueryCache } from 'react-query'
import { useHistory } from 'react-router-dom'
import { useApplicationNavigation } from '@hooks'
import { CenterMessage } from '@components/common'
import { TableCancel } from 'mdi-material-ui'

interface Props {
  isList: boolean,
  loading: boolean,
  clubs: Club[],
  role?: AuthRoles,
  onResetFilters: () => void
}

export const ClubsViewSelector = ({
  isList,
  clubs,
  loading,
  role,
  onResetFilters
}: Props) => {
  const queryCache = useQueryCache()
  const { onRoutePreload } = useApplicationNavigation()
  const { push } = useHistory()

  const onClubSelect = useCallback((set: Club) => () => {
    queryCache.setQueryData([Client.GET_CLUB_BY_SLUG, set.slug], set)
    queryCache.setQueryData([Client.GET_CLUB_BY_ID, set.id], set)
    push(`/dashboard/club/${set.slug}`)
  }, [push, queryCache])

  if (clubs.length === 0 && !loading) {
    return (
      <CenterMessage
        icon={TableCancel}
        text='No clubs found with selected filters.'
        onClick={onResetFilters}
        buttonText='Reset filters'
      />
    )
  }

  if (isList) {
    return (
      <ClubListView
        loading={loading}
        clubs={clubs}
        role={role}
        onRoutePreload={onRoutePreload}
        onClubSelect={onClubSelect}
      />
    )
  }

  return (
    <div />
  )
}
