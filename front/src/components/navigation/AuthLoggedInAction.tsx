import React, { Fragment, useState, useCallback } from 'react'
import { MenuDown } from 'mdi-material-ui'
import { AuthUser } from '@types'
import { customPalette } from '@theme'
import clsx from 'clsx'

import {
  createStyles,
  makeStyles,
  Button,
  Typography as T,
  Avatar,
  Popper,
  Paper,
  ClickAwayListener,
  Divider,
  Hidden,
  Grow
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  loggedIn: {
    display: 'flex',
    alignSelf: 'center',
    cursor: 'pointer',
    userSelect: 'none'
  },
  avatar: {
    color: theme.palette.common.white,
    marginRight: theme.spacing(1),
    fontSize: theme.typography.body1.fontSize,
    backgroundColor: customPalette.header.primaryText,
    [theme.breakpoints.down('sm')]: {
      marginRight: theme.spacing(0)
    }
  },
  isMobile: {
    marginRight: theme.spacing(0)
  },
  innerAvatar: {
    color: theme.palette.common.white,
    marginBottom: theme.spacing(0.75),
    fontSize: theme.typography.h3.fontSize,
    backgroundColor: customPalette.header.primaryText,
    width: '100px',
    height: '100px'
  },
  text: {
    alignSelf: 'center',
    color: customPalette.header.primaryText
  },
  popper: {
    marginTop: theme.spacing(1.5),
    zIndex: theme.zIndex.appBar - 50
  },
  top: {
    marginBottom: theme.spacing(1),
    textAlign: 'center',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center'
  },
  bottom: {
    marginTop: theme.spacing(1),
    textAlign: 'center'
  },
  rootPaper: {
    borderRadius: theme.spacing(1),
    minWidth: '240px',
    maxWidth: '240px'
  },
  wrap: {
    padding: theme.spacing(2),
    '& > div:not(:last-child)': {
      marginBottom: theme.spacing(1)
    }
  },
  subtext: {
    color: theme.palette.text.secondary
  },
  open: {
    transform: 'rotate(-180deg)'
  },
  icon: {
    transition: 'transform .3s cubic-bezier(0.4, 0, 0.2, 1)'
  }
}))

const getFirstLetters = (name: string) => {
  return name.charAt(0)
}

interface Props {
  auth: AuthUser,
  revokeAuth: () => void
}

export const AuthLoggedInAction = ({
  auth,
  revokeAuth
}: Props) => {
  const classes = useStyles()
  const [anchorEl, setAnchorEl] = useState<HTMLDivElement | null>(null)
  const [open, setOpen] = useState(false)

  const handleClick = useCallback((event: React.MouseEvent<HTMLDivElement>) => {
    setAnchorEl(event.currentTarget)
    setOpen(prev => !prev)
  }, [setAnchorEl, setOpen])

  const closeDialog = useCallback(() => {
    setOpen(false)
  }, [setOpen])

  if (!auth) {
    return <Fragment />
  }

  return (
    <>
      <Popper
        open={open}
        anchorEl={anchorEl}
        placement='bottom'
        transition
        className={classes.popper}
      >
        {({ TransitionProps }) => (
          <ClickAwayListener onClickAway={closeDialog}>
            <Grow
              {...TransitionProps}
              style={{ transformOrigin: 'center top' }}
              timeout={200}
            >
              <Paper
                square
                classes={{ root: classes.rootPaper }}
              >
                <div className={classes.wrap}>
                  <div className={classes.top}>
                    <Avatar
                      className={classes.innerAvatar}
                    >{getFirstLetters(auth.user.username).toUpperCase()}</Avatar>
                  </div>
                  <div className={classes.bottom}>
                    <T variant='body1'>{auth.user.username}</T>
                    <T variant='body2' className={classes.subtext}>{auth.user.role}</T>
                  </div>
                </div>
                <Divider variant='fullWidth' />
                <div className={classes.wrap}>
                  <Hidden mdUp>
                    <div>
                      <Button
                        variant='outlined'
                        color='default'
                        fullWidth
                        onClick={() => {}}
                      >Settings</Button>
                    </div>
                  </Hidden>
                  <div>
                    <Button
                      variant='outlined'
                      color='default'
                      fullWidth
                      onClick={revokeAuth}
                    >Logout</Button>
                  </div>
                </div>
              </Paper>
            </Grow>
          </ClickAwayListener>
        )}
      </Popper>
      <div
        className={classes.loggedIn}
        onClick={handleClick}
      >
        <Avatar
          className={classes.avatar}
        >{getFirstLetters(auth.user.username).toUpperCase()}</Avatar>
        <Hidden smDown>
          <>
            <T className={classes.text}>{auth.user.username}</T>
            <MenuDown
              className={clsx(classes.text, classes.icon, {
                [classes.open]: open
              })}
            />
          </>
        </Hidden>
      </div>
    </>
  )
}
