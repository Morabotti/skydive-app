import React from 'react'
import { ActivityListRow } from '@components/activities'
import { Activity } from '@types'
import { AuthRoles } from '@enums'
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
  activities: Activity[],
  role?: AuthRoles,
  onActivitySelect: (set: Activity) => () => void,
  onRoutePreload: (set: string) => () => void
}

export const ActivityListView = ({
  activities,
  loading,
  role,
  onActivitySelect,
  onRoutePreload
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
              {role === AuthRoles.ADMIN && (
                <TableCell>
                  <Skeleton variant='text' width='40%' height={23} />
                </TableCell>
              )}
              <TableCell />
            </TableRow>
          </TableHead>
          <TableBody>
            {[...Array(12)].map((e, i) => (
              <ActivityListRow key={i} loading role={role} />
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
            <TableCell className={classes.header}>Title</TableCell>
            <TableCell className={classes.header}>Club</TableCell>
            <TableCell className={classes.header}>Type</TableCell>
            <TableCell className={classes.header}>Access</TableCell>
            <TableCell className={classes.header}>Date</TableCell>
            {role === AuthRoles.ADMIN && (
              <TableCell className={classes.header}>Is Visible</TableCell>
            )}
            <TableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {activities.map(activity => (
            <ActivityListRow
              key={activity.token}
              activity={activity}
              loading={loading}
              role={role}
              onMouseEnter={onRoutePreload(`/dashboard/activity/${activity.token}`)}
              onClick={onActivitySelect(activity)}
            />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
