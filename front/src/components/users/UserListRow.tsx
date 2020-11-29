import React from 'react'
import { User } from '@types'
import { ChevronRight, Cog } from 'mdi-material-ui'
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
    transition: `opacity 300ms ${theme.transitions.easing.easeInOut}`,
    display: 'flex'
  }
}))

interface Props {
  loading?: boolean,
  user?: User,
  onClick?: () => void,
  onMouseEnter?: () => void,
  onSettings?: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void
}

export const UserListRow = ({
  user,
  loading,
  onMouseEnter,
  onClick,
  onSettings
}: Props) => {
  const classes = useStyles()

  if (!user || loading) {
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
        <TableCell padding='checkbox' align='right'>
          <Skeleton variant='circle' width={48} height={48} />
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
      <TableCell>{user.profile?.firstName} {user.profile?.lastName}</TableCell>
      <TableCell>{user.username}</TableCell>
      <TableCell>{user.role}</TableCell>
      <TableCell>{user.profile?.city}</TableCell>
      <TableCell>
        {user.deletedAt === null
          ? ''
          : moment(user.deletedAt).format('DD.MM.YYYY HH:mm')
        }
      </TableCell>
      <TableCell padding='checkbox' align='right'>
        <div className={classes.show}>
          <Tooltip title='Open actions'>
            <IconButton onClick={onSettings}>
              <Cog />
            </IconButton>
          </Tooltip>
          <Tooltip title='To profile'>
            <IconButton>
              <ChevronRight />
            </IconButton>
          </Tooltip>
        </div>
      </TableCell>
    </TableRow>
  )
}
