import React, { useCallback } from 'react'
import { UserListView, UserBlockList } from '@components/users'
import { User } from '@types'
import { Client } from '@enums'
import { useQueryCache } from 'react-query'
import { useHistory } from 'react-router-dom'
import { useApplicationNavigation } from '@hooks'
import { CenterMessage } from '@components/common'
import { AccountCancel } from 'mdi-material-ui'

interface Props {
  isList: boolean,
  loading: boolean,
  users: User[],
  onResetFilters: () => void,
  onSelectSettings: (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const UsersViewSelector = ({
  isList,
  users,
  loading,
  onResetFilters,
  onSelectSettings
}: Props) => {
  const queryCache = useQueryCache()
  const { onRoutePreload } = useApplicationNavigation()
  const { push } = useHistory()

  const onUserSelect = useCallback((set: User) => () => {
    queryCache.setQueryData([Client.GET_USER_BY_ID, set.id], set)
    push(`/dashboard/users/${set.id}`)
  }, [push, queryCache])

  if (users.length === 0 && !loading) {
    return (
      <CenterMessage
        icon={AccountCancel}
        text='No users found with selected filters.'
        onClick={onResetFilters}
        buttonText='Reset filters'
      />
    )
  }

  if (isList) {
    return (
      <UserListView
        loading={loading}
        users={users}
        onRoutePreload={onRoutePreload}
        onUserSelect={onUserSelect}
        onSelectSettings={onSelectSettings}
      />
    )
  }

  return (
    <UserBlockList
      loading={loading}
      users={users}
      onRoutePreload={onRoutePreload}
      onUserSelect={onUserSelect}
      onSelectSettings={onSelectSettings}
    />
  )
}
