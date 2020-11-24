import React from 'react'
import { ClubListRow } from '@components/clubs'
import { Club } from '@types'
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
  clubs: Club[],
  role?: AuthRoles,
  onClubSelect: (set: Club) => () => void,
  onRoutePreload: (set: string) => () => void
}

export const ClubListView = ({
  clubs,
  loading,
  role,
  onClubSelect,
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
              <ClubListRow key={i} loading role={role} />
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
            <TableCell className={classes.header}>City</TableCell>
            {role === AuthRoles.ADMIN && (
              <TableCell className={classes.header}>Is Public</TableCell>
            )}
            <TableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {clubs.map(club => (
            <ClubListRow
              key={club.slug}
              club={club}
              loading={loading}
              role={role}
              onMouseEnter={onRoutePreload(`/dashboard/club/${club.slug}`)}
              onClick={onClubSelect(club)}
            />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}
