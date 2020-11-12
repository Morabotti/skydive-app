import React, { memo } from 'react'
import { Cog, Bell } from 'mdi-material-ui'
import { Route, AuthUser } from '@types'
import { customPalette } from '@theme'
import { AuthLoggedInAction, AuthLocationNavigation } from '@components/navigation'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  AppBar,
  Toolbar,
  Grid,
  IconButton,
  Tooltip,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  primaryBar: {
    transition: 'background-color .15s cubic-bezier(0.4, 0, 0.2, 1)',
    overflow: 'hidden',
    backgroundColor: theme.palette.common.white
  },
  hiddenOverflow: {
    overflow: 'hidden'
  },
  headerButton: {
    border: 'none',
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
  navigationButton: {
    transform: 'translate(0, 64px)',
    opacity: '0',
    visibility: 'hidden',
    transition: 'opacity .15s ease, transform .15s ease, visibility .15s linear'
  },
  navigateBackToTop: {
    opacity: '1',
    transform: 'translate(0, 0)',
    visibility: 'visible'
  },
  navigationText: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    color: customPalette.header.primaryText,
    fontWeight: 600,
    userSelect: 'none'
  },
  primaryText: {
    color: customPalette.header.primaryText
  }
}))

interface Props {
  auth: AuthUser,
  currentRoute: Route | undefined,
  onRevokeAuth: () => void
}

export const AuthNavigationHeader = memo(({
  auth,
  currentRoute,
  onRevokeAuth
}: Props) => {
  const classes = useStyles()

  const onNavigateTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  return (
    <AppBar
      color='transparent'
      position='sticky'
      elevation={3}
      className={classes.primaryBar}
    >
      <Toolbar>
        <Grid container spacing={2} alignItems='center'>
          <Grid item>
            <AuthLocationNavigation />
          </Grid>
          <Grid item xs className={classes.hiddenOverflow}>
            <button
              className={clsx(classes.headerButton, classes.navigationButton, classes.navigateBackToTop)}
              onClick={onNavigateTop}
            >
              <T
                variant='body1'
                className={classes.navigationText}
              >{currentRoute?.name}</T>
            </button>
          </Grid>
          <Grid item>
            <Tooltip title='Notifications'>
              <IconButton
                color='inherit'
                className={classes.primaryText}
                onClick={() => {}}
              >
                <Bell />
              </IconButton>
            </Tooltip>
            <Tooltip title='Settings'>
              <IconButton
                color='inherit'
                className={classes.primaryText}
                onClick={() => {}}
              >
                <Cog />
              </IconButton>
            </Tooltip>
          </Grid>
          <Grid item>
            <AuthLoggedInAction
              auth={auth}
              revokeAuth={onRevokeAuth}
            />
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  )
})
