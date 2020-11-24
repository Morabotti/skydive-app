import React, { useState, useCallback, useRef, useEffect } from 'react'
import { Apps } from 'mdi-material-ui'
import { customPalette } from '@theme'
import { useLocation } from 'react-router-dom'
import { AuthUser, Route } from '@types'
import { authNavigation } from '@routes'
import clsx from 'clsx'

import {
  createStyles,
  makeStyles,
  Popper,
  Paper,
  ClickAwayListener,
  Grow,
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText
} from '@material-ui/core'
import { useApplicationNavigation } from '@hooks'

const useStyles = makeStyles(theme => createStyles({
  icon: {
    color: customPalette.header.primaryText,
    padding: theme.spacing(1),
    '& > span > svg': {
      height: 32,
      width: 32
    }
  },
  popper: {
    marginTop: theme.spacing(1),
    zIndex: theme.zIndex.appBar - 50
  },
  rootPaper: {
    borderRadius: theme.spacing(1),
    minWidth: '260px',
    maxWidth: '260px',
    borderTopRightRadius: 0,
    borderTopLeftRadius: 0
  },
  list: {
    width: '100%'
  },
  selected: {
    color: customPalette.header.primaryColor
  }
}))

interface Props {
  auth: AuthUser,
  currentRoute: Route | undefined
}

export const AuthLocationNavigator = ({
  auth,
  currentRoute
}: Props) => {
  const classes = useStyles()
  const { pathname } = useLocation()
  const { onNavigation, onRoutePreload } = useApplicationNavigation()

  const [open, setOpen] = useState(false)
  const prevOpen = useRef(open)
  const anchorRef = useRef<HTMLButtonElement>(null)

  const handleMenu = useCallback(() => {
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
                <List component='nav' className={classes.list}>
                  {authNavigation.filter(i => i.access.includes(auth.user.role))
                    .map(route => (
                      <ListItem
                        key={route.path}
                        button
                        selected={route.path === currentRoute?.path}
                        onClick={onNavigation(route.path)}
                        onMouseEnter={onRoutePreload(route.path)}
                      >
                        {route.icon && (
                          <ListItemIcon>
                            <route.icon
                              className={clsx({
                                [classes.selected]: route.path === currentRoute?.path
                              })}
                            />
                          </ListItemIcon>
                        )}
                        <ListItemText primary={route.name} />
                      </ListItem>
                    ))
                  }
                </List>
              </Paper>
            </Grow>
          </ClickAwayListener>
        )}
      </Popper>
      <IconButton
        color='inherit'
        className={classes.icon}
        onClick={handleMenu}
        ref={anchorRef}
        edge='start'
      >
        <Apps />
      </IconButton>
    </>
  )
}
