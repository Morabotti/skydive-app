import React, { Fragment, useState, useCallback, useRef, useEffect } from 'react'
import { MenuDown } from 'mdi-material-ui'
import { AuthUser } from '@types'
import { customPalette } from '@theme'
import clsx from 'clsx'
import { useLocation } from 'react-router-dom'

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
    fontSize: theme.typography.h4.fontSize,
    backgroundColor: customPalette.header.primaryText,
    width: '100px',
    height: '100px'
  },
  text: {
    alignSelf: 'center',
    color: customPalette.header.primaryText
  },
  popper: {
    marginTop: theme.spacing(1),
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
    maxWidth: '240px',
    borderTopRightRadius: 0,
    borderTopLeftRadius: 0
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

const getLetters = (firstName: string, lastName: string) => {
  return `${firstName.charAt(0).toUpperCase()}${lastName.charAt(0).toUpperCase()}`
}

interface Props {
  auth: AuthUser,
  onRevokeAuth: () => void,
  onSettings: () => void
}

export const AuthLoggedInAction = ({
  auth,
  onRevokeAuth,
  onSettings
}: Props) => {
  const classes = useStyles()
  const { pathname } = useLocation()
  const [open, setOpen] = useState(false)
  const prevOpen = useRef(open)
  const anchorRef = useRef<HTMLDivElement>(null)
  const profile = auth.user.profile

  const handleMenu = useCallback(() => {
    setOpen(prev => !prev)
  }, [setOpen])

  const onMenuSelect = useCallback((cb: () => void) => () => {
    setOpen(false)
    cb()
  }, [setOpen])

  const handleClose = useCallback((e: React.MouseEvent<EventTarget>) => {
    if (anchorRef.current && anchorRef.current.contains(e.target as HTMLButtonElement)) {
      return
    }

    setOpen(false)
  }, [setOpen])

  useEffect(() => {
    if (prevOpen.current === true && open === false) {
      anchorRef.current?.focus()
    }

    prevOpen.current = open
  }, [open])

  useEffect(() => {
    setOpen(false)
  }, [pathname, setOpen])

  if (!auth || !profile) {
    return <Fragment />
  }

  return (
    <>
      <Popper
        open={open}
        anchorEl={anchorRef.current}
        placement='bottom'
        transition
        className={classes.popper}
      >
        {({ TransitionProps }) => (
          <ClickAwayListener onClickAway={handleClose}>
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
                    >{getLetters(profile.firstName, profile.lastName).toUpperCase()}</Avatar>
                  </div>
                  <div className={classes.bottom}>
                    <T variant='body1'>{profile.firstName} {profile.lastName}</T>
                    <T variant='body2' className={classes.subtext}>{auth.user.username}</T>
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
                        onClick={onMenuSelect(onSettings)}
                      >Settings</Button>
                    </div>
                  </Hidden>
                  <div>
                    <Button
                      variant='outlined'
                      color='default'
                      fullWidth
                      onClick={onRevokeAuth}
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
        onClick={handleMenu}
        ref={anchorRef}
      >
        <Avatar
          className={classes.avatar}
        >{getLetters(profile.firstName, profile.lastName).toUpperCase()}</Avatar>
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
