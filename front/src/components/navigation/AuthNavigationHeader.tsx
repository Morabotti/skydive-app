import React, { memo } from 'react'
import { Cog } from 'mdi-material-ui'
import { Route, AuthUser } from '@types'
import { customPalette } from '@theme'
import clsx from 'clsx'

import {
  makeStyles,
  createStyles,
  AppBar,
  Toolbar,
  Grid,
  IconButton,
  Tooltip,
  Typography as T,
  Hidden,
  LinearProgress
} from '@material-ui/core'

import {
  AuthLoggedInAction,
  AuthNotifications,
  AuthLocationNavigator
} from '@components/navigation'
import { useDashboard } from '@hooks'

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
  },
  location: {
    color: customPalette.header.primaryText,
    height: 38,
    width: 38
  },
  center: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center'
  },
  loading: {
    position: 'absolute',
    zIndex: theme.zIndex.appBar - 1,
    top: 64,
    left: 0,
    width: '100%',
    animation: `$loadingAnimation 400ms ${theme.transitions.easing.easeInOut}`,
    [theme.breakpoints.down('xs')]: {
      top: 56
    }
  },
  '@keyframes loadingAnimation': {
    'from': {
      opacity: 0
    },
    'to': {
      opacity: 1
    }
  }
}))

interface Props {
  auth: AuthUser,
  currentRoute: Route | undefined,
  onRevokeAuth: () => void,
  onSettings: () => void
}

export const AuthNavigationHeader = memo(({
  auth,
  currentRoute,
  onRevokeAuth,
  onSettings
}: Props) => {
  const classes = useStyles()
  const { loading } = useDashboard()

  const onNavigateTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  return (
    <>
      <AppBar
        color='transparent'
        position='sticky'
        elevation={3}
        className={classes.primaryBar}
      >
        <Toolbar>
          <Grid container spacing={2} alignItems='center'>
            <Grid item>
              <AuthLocationNavigator
                auth={auth}
                currentRoute={currentRoute}
              />
            </Grid>
            <Grid item xs className={classes.hiddenOverflow}>
              <button
                className={clsx(
                  classes.headerButton,
                  classes.navigationButton, {
                    [classes.navigateBackToTop]: currentRoute?.name !== undefined
                  }
                )}
                onClick={onNavigateTop}
              >
                <T
                  variant='body1'
                  className={classes.navigationText}
                >{currentRoute?.name}</T>
              </button>
            </Grid>
            <Grid item>
              <AuthNotifications />
              <Hidden smDown>
                <Tooltip title='Settings'>
                  <IconButton
                    color='inherit'
                    className={classes.primaryText}
                    onClick={onSettings}
                  >
                    <Cog />
                  </IconButton>
                </Tooltip>
              </Hidden>
            </Grid>
            <Grid item>
              <AuthLoggedInAction
                auth={auth}
                onRevokeAuth={onRevokeAuth}
                onSettings={onSettings}
              />
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
      {loading && (
        <LinearProgress className={classes.loading} />
      )}
    </>
  )
})
