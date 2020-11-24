import React, { useCallback } from 'react'
import { ClubListView } from '@components/clubs'
import { Club } from '@types'
import { AuthRoles, Client } from '@enums'
import { useQueryCache } from 'react-query'
import { useHistory } from 'react-router-dom'
import { useApplicationNavigation } from '@hooks'

interface Props {
  isList: boolean,
  loading: boolean,
  clubs: Club[],
  role?: AuthRoles
}

export const ClubsViewSelector = ({
  isList,
  clubs,
  loading,
  role
}: Props) => {
  const queryCache = useQueryCache()
  const { onRoutePreload } = useApplicationNavigation()
  const { push } = useHistory()

  const onClubSelect = useCallback((set: Club) => () => {
    queryCache.setQueryData([Client.GET_CLUB_BY_SLUG, set.slug], set)
    push(`/dashboard/club/${set.slug}`)
  }, [push, queryCache])

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
