import React from 'react'
import { UserListRow } from '@components/users'
import { User } from '@types'
import { Skeleton } from '@material-ui/lab'
import { customPalette } from '@theme'

import {
  createStyles,
  makeStyles,
  Table,
  TableCell,
  TableHead,
  TableRow,
  TableBody,
  TableContainer,
  Paper
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  header: {
    fontWeight: theme.typography.fontWeightBold,
    color: customPalette.header.primaryText
  }
}))

interface Props {
  loading: boolean,
  users: User[],
  onUserSelect: (set: User) => () => void,
  onRoutePreload: (set: string) => () => void,
  onSelectSettings: (user: User) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const UserListView = ({
  users,
  loading,
  onUserSelect,
  onRoutePreload,
  onSelectSettings
}: Props) => {
  const classes = useStyles()

  if (loading) {
    return (
      <TableContainer component={Paper} square>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>
                <Skeleton variant='text' width='60%' height={23} />
              </TableCell>
              <TableCell>
                <Skeleton variant='text' width='50%' height={23} />
              </TableCell>
              <TableCell>
                <Skeleton variant='text' width='50%' height={23} />
              </TableCell>
              <TableCell>
                <Skeleton variant='text' width='50%' height={23} />
              </TableCell>
              <TableCell>
                <Skeleton variant='text' width='60%' height={23} />
              </TableCell>
              <TableCell />
            </TableRow>
          </TableHead>
          <TableBody>
            {[...Array(12)].map((e, i) => (
              <UserListRow key={i} loading />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    )
  }

  return (
    <TableContainer
      component={Paper}
      square
    >
      <Table>
        <TableHead>
          <TableRow>
            <TableCell className={classes.header}>Name</TableCell>
            <TableCell className={classes.header}>Email</TableCell>
            <TableCell className={classes.header}>Role</TableCell>
            <TableCell className={classes.header}>City</TableCell>
            <TableCell className={classes.header}>Deleted</TableCell>
            <TableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {users.map(user => (
            <UserListRow
              key={user.id}
              user={user}
              loading={loading}
              onMouseEnter={onRoutePreload(`/dashboard/users/${user.id}`)}
              onClick={onUserSelect(user)}
              onSettings={onSelectSettings(user)}
            />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
