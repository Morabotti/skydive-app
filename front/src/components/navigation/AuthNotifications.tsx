import React, { useState, useCallback, useRef, useEffect } from 'react'
import { Bell } from 'mdi-material-ui'
import { customPalette } from '@theme'

import {
  createStyles,
  makeStyles,
  Popper,
  Paper,
  ClickAwayListener,
  Grow,
  Tooltip,
  IconButton
} from '@material-ui/core'
import { useLocation } from 'react-router-dom'

const useStyles = makeStyles(theme => createStyles({
  primaryText: {
    color: customPalette.header.primaryText
  },
  popper: {
    marginTop: theme.spacing(1),
    zIndex: theme.zIndex.appBar - 50
  },
  rootPaper: {
    borderRadius: theme.spacing(1),
    minWidth: '240px',
    maxWidth: '240px',
    borderTopRightRadius: 0,
    borderTopLeftRadius: 0
  }
}))

export const AuthNotifications = () => {
  const classes = useStyles()
  const { pathname } = useLocation()
  const [open, setOpen] = useState(false)
  const prevOpen = useRef(open)
  const anchorRef = useRef<HTMLButtonElement>(null)

  const handleNotificationMenu = useCallback(() => {
    setOpen(prev => !prev)
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
                elevation={3}
              >
                <div>
                  TODO
                </div>
              </Paper>
            </Grow>
          </ClickAwayListener>
        )}
      </Popper>
      <Tooltip title='Notifications'>
        <IconButton
          color='inherit'
          className={classes.primaryText}
          onClick={handleNotificationMenu}
          ref={anchorRef}
        >
          <Bell />
        </IconButton>
      </Tooltip>
    </>
  )
}
