import React, { memo, useCallback, useState, useEffect, useRef } from 'react'
import { useLocation } from 'react-router-dom'
import clsx from 'clsx'
import { MenuDown } from 'mdi-material-ui'
import { customPalette } from '@theme'

import {
  makeStyles,
  createStyles,
  Typography as T,
  Popper,
  Grow,
  Paper,
  ClickAwayListener,
  MenuList,
  MenuItem
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  open: {
    transform: 'rotate(-180deg)'
  },
  toggle: {
    fontSize: '1.5em !important',
    marginLeft: theme.spacing(0.25),
    transition: 'transform .3s cubic-bezier(0.4, 0, 0.2, 1)'
  },
  locationBtnOffset: {
    marginRight: theme.spacing(2)
  },
  locationText: {
    color: customPalette.header.secondaryText,
    userSelect: 'none',
    fontWeight: 500
  },
  headerButton: {
    border: 'none',
    display: 'flex',
    margin: theme.spacing(0),
    padding: theme.spacing(0),
    color: customPalette.header.secondaryText,
    width: 'auto',
    background: 'transparent',
    overflow: 'visible',
    lineHeight: 'normal',
    outline: 'none',
    cursor: 'pointer',
    '-webkit-font-smoothing': 'inherit',
    '-moz-osx-font-smoothing': 'inherit',
    '-webkit-appearance': 'none',
    '&::-moz-focus-inner': {
      border: theme.spacing(0),
      padding: theme.spacing(0)
    }
  },
  popper: {
    marginTop: theme.spacing(2.5),
    zIndex: theme.zIndex.drawer - 50
  },
  selected: {
    backgroundColor: theme.palette.action.selected
  }
}))

export const AuthLocationNavigation = memo(() => {
  const classes = useStyles()
  const { pathname } = useLocation()
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
      <button
        className={clsx(classes.headerButton, classes.locationBtnOffset)}
        onClick={handleMenu}
        ref={anchorRef}
      >
        <T variant='body1' className={classes.locationText}>Project 1</T>
        <MenuDown
          className={clsx(classes.toggle, {
            [classes.open]: open
          })}
        />
      </button>
      <Popper
        open={open}
        anchorEl={anchorRef.current}
        placement='bottom-start'
        transition
        className={classes.popper}
      >
        {({ TransitionProps }) => (
          <Grow
            {...TransitionProps}
            style={{ transformOrigin: 'center top' }}
            timeout={200}
          >
            <Paper elevation={2} square>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList autoFocusItem={open}>
                  <MenuItem onClick={() => {}}>
                    <T variant='inherit'>Change project</T>
                  </MenuItem>
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>
    </>
  )
})
