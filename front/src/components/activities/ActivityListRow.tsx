import React from 'react'
import { Activity } from '@types'
import { ActivityAccess, AuthRoles } from '@enums'
import { ChevronRight } from 'mdi-material-ui'
import { Skeleton } from '@material-ui/lab'
import moment from 'moment'

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
  activity?: Activity,
  role?: AuthRoles,
  onClick?: () => void,
  onMouseEnter?: () => void
}

export const ActivityListRow = ({
  activity,
  loading,
  role,
  onMouseEnter,
  onClick
}: Props) => {
  const classes = useStyles()

  if (!activity || loading) {
    return (
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
      <TableCell>{activity.title}</TableCell>
      <TableCell>{activity.club?.name}</TableCell>
      <TableCell>{activity.type.toUpperCase()}</TableCell>
      <TableCell>{activity.access === ActivityAccess.OPEN ? 'OPEN' : 'INVITE ONLY'}</TableCell>
      <TableCell>{moment(activity.startDate).format('DD.MM.YYYY')}</TableCell>
      {role === AuthRoles.ADMIN && (
        <TableCell>{activity.visible ? 'Yes' : 'No'}</TableCell>
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
