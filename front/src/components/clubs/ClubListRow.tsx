import React from 'react'
import { Club } from '@types'
import { AuthRoles } from '@enums'
import { ChevronRight } from 'mdi-material-ui'
import { Skeleton } from '@material-ui/lab'

import {
  createStyles,
  makeStyles,
  TableRow,
  TableCell,
  IconButton,
  Tooltip
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  hoverable: {
    cursor: 'pointer',
    '&:hover $show': {
      opacity: 1
    }
  },
  show: {
    opacity: 0,
    transition: `opacity 300ms ${theme.transitions.easing.easeInOut}`
  }
}))

interface Props {
  loading?: boolean,
  club?: Club,
  role?: AuthRoles,
  onClick?: () => void,
  onMouseEnter?: () => void
}

export const ClubListRow = ({
  club,
  loading,
  role,
  onMouseEnter,
  onClick
}: Props) => {
  const classes = useStyles()

  if (!club || loading) {
    return (
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
        <TableCell padding='checkbox' align='right'>
          <Skeleton variant='circle' width={48} height={48} />
        </TableCell>
      </TableRow>
    )
  }

  return (
    <TableRow
      hover
      className={classes.hoverable}
      onClick={onClick}
      onMouseEnter={onMouseEnter}
    >
      <TableCell>{club.name}</TableCell>
      <TableCell>{club.clubProfile.city}</TableCell>
      {role === AuthRoles.ADMIN && (
        <TableCell>{club.isPublic ? 'Yes' : 'No'}</TableCell>
      )}
      <TableCell padding='checkbox' align='right'>
        <Tooltip title='To profile' className={classes.show}>
          <IconButton>
            <ChevronRight />
          </IconButton>
        </Tooltip>
      </TableCell>
    </TableRow>
  )
}
