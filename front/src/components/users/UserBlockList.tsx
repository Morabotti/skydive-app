import React from 'react'
import { UserBlock } from '@components/users'
import { User } from '@types'
import { Grid } from '@material-ui/core'

interface Props {
  loading: boolean,
  users: User[],
  onUserSelect: (set: User) => () => void,
  onRoutePreload: (set: string) => () => void,
  onSelectSettings: (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const UserBlockList = ({
  users,
  loading,
  onUserSelect,
  onRoutePreload,
  onSelectSettings
}: Props) => {
  if (loading) {
    return (
      <Grid container spacing={2}>
        {[...Array(12)].map((e, i) => (
          <Grid key={i} item xs={6} sm={4} md={3} xl={2}>
            <UserBlock loading />
          </Grid>
        ))}
      </Grid>
    )
  }

  return (
    <>
      <Grid container spacing={2}>
        {users.map(user => (
          <Grid key={user.id} item xs={6} sm={4} md={3} xl={2}>
            <UserBlock
              loading={loading}
              user={user}
              onMouseEnter={onRoutePreload(`/dashboard/users/${user.id}`)}
              onClick={onUserSelect(user)}
              onSettings={onSelectSettings(user)}
            />
          </Grid>
        ))}
      </Grid>
    </>
  )
}
