import React from 'react'
import { ClubAccount } from '@types'
import { Cancel, Check } from 'mdi-material-ui'
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
  },
  flex: {
    display: 'flex'
  }
}))

interface Props {
  loading?: boolean,
  member?: ClubAccount,
  isOwner: boolean,
  onAccept?: () => void,
  onDecline?: () => void,
  onRemove?: () => void
}

export const ClubUserListRow = ({
  loading,
  member,
  isOwner,
  onAccept,
  onDecline,
  onRemove
}: Props) => {
  const classes = useStyles()

  if (!member || loading) {
    return (
      <TableRow>
        <TableCell>
          <Skeleton variant='text' width='60%' height={23} />
        </TableCell>
        {isOwner && (
          <TableCell>
            <Skeleton variant='text' width='50%' height={23} />
          </TableCell>
        )}
        <TableCell>
          <Skeleton variant='text' width='50%' height={23} />
        </TableCell>
        <TableCell>
          <Skeleton variant='text' width='50%' height={23} />
        </TableCell>
        {isOwner && (
          <TableCell padding='checkbox' align='right'>
            <div className={classes.flex}>
              <Skeleton variant='circle' width={48} height={48} />
              <Skeleton variant='circle' width={48} height={48} />
            </div>
          </TableCell>
        )}
      </TableRow>
    )
  }

  return (
    <TableRow className={classes.hoverable}>
      <TableCell>
        {member.account?.profile?.firstName} {member.account?.profile?.lastName}
      </TableCell>
      {isOwner && (
        <TableCell>{member.account?.username}</TableCell>
      )}
      <TableCell>{member.role.toLocaleLowerCase()}</TableCell>
      <TableCell>
        {member.accepted !== null
          ? moment(member.accepted).format('DD.MM.YYYY')
          : 'Not Accepted'
        }
      </TableCell>
      {isOwner && (
        <TableCell padding='checkbox' align='right'>
          {member.accepted === null ? (
            <div className={classes.show}>
              <Tooltip title='Accept member'>
                <IconButton onClick={onAccept}>
                  <Check />
                </IconButton>
              </Tooltip>
              <Tooltip title='Cancel request'>
                <IconButton onClick={onDecline}>
                  <Cancel />
                </IconButton>
              </Tooltip>
            </div>
          ) : (
            <div className={classes.show}>
              <Tooltip title='Remove member'>
                <IconButton onClick={onRemove}>
                  <Cancel />
                </IconButton>
              </Tooltip>
            </div>
          )}
        </TableCell>
      )}
    </TableRow>
  )
}
