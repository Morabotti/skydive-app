import React from 'react'
import { User } from '@types'
import clsx from 'clsx'
import { Account, Cog } from 'mdi-material-ui'
import { AuthRoles } from '@enums'

import {
  Avatar,
  colors,
  createStyles,
  IconButton,
  makeStyles,
  Paper,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  button: {
    outline: 0,
    padding: 0,
    border: 0,
    cursor: 'pointer',
    textAlign: 'left',
    width: '100%',
    height: '280px',
    display: 'flex',
    position: 'relative',
    '&:hover $avatar > svg': {
      width: '84%',
      height: '84%'
    }
  },
  wrap: {
    padding: theme.spacing(3),
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    width: '100%',
    height: '100%',
    [theme.breakpoints.down('xs')]: {
      padding: theme.spacing(2)
    }
  },
  top: {
    marginBottom: theme.spacing(2)
  },
  avatar: {
    width: '120px',
    height: '120px',
    '& > svg': {
      width: '70%',
      height: '70%',
      transition: `all 300ms ${theme.transitions.easing.easeInOut}`,
      color: '#fff'
    }
  },
  admin: {
    backgroundColor: colors.deepOrange[500]
  },
  user: {
    backgroundColor: colors.blue[500]
  },
  actions: {
    position: 'absolute',
    top: '4px',
    right: '4px'
  }
}))

interface Props {
  loading: boolean,
  user?: User,
  onClick?: () => void,
  onMouseEnter?: () => void,
  onSettings?: (e: React.MouseEvent<HTMLButtonElement>) => void
}

export const UserBlock = ({
  user,
  loading,
  onClick,
  onMouseEnter,
  onSettings
}: Props) => {
  const classes = useStyles()

  if (loading || !user) {
    return (
      <Paper square className={classes.button}>
        LMAO
      </Paper>
    )
  }

  return (
    <Paper
      component='button'
      onMouseEnter={onMouseEnter}
      onClick={onClick}
      square
      className={classes.button}
    >
      <div className={classes.wrap}>
        <div className={classes.top}>
          <Avatar
            className={clsx(classes.avatar, {
              [classes.admin]: user.role === AuthRoles.ADMIN && user.deletedAt === null,
              [classes.user]: user.role === AuthRoles.USER && user.deletedAt === null
            })}
          >
            <Account />
          </Avatar>
        </div>
        <div>
          <T variant='body1' align='center'>{user.profile?.firstName} {user.profile?.lastName}</T>
          <T variant='body1' color='textSecondary' align='center' gutterBottom>{user.username}</T>
          <T variant='body2' color='textSecondary' align='center'>{user.role.toUpperCase()}</T>
        </div>
      </div>
      <div className={classes.actions}>
        <IconButton onClick={onSettings}>
          <Cog />
        </IconButton>
      </div>
    </Paper>
  )
}
