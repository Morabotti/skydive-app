import React, { memo, useMemo } from 'react'
import { Menu, Cog } from 'mdi-material-ui'
import { useLocation } from 'react-router-dom'
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
  Hidden,
  IconButton,
  Tooltip,
  Tabs,
  Tab,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  primaryBar: {
    transition: 'background-color .15s cubic-bezier(0.4, 0, 0.2, 1)',
    overflow: 'hidden'
  },
  solidBar: {
    backgroundColor: customPalette.common.background
  },
  secondaryBar: {
    zIndex: 0,
    opacity: 1,
    transition: 'opacity .1s cubic-bezier(0.4, 0, 0.2, 1)'
  },
  hideBar: {
    opacity: 0
  },
  thirdToolBar: {
    minHeight: theme.spacing(6),
    boxShadow: `0 -1px 0 ${customPalette.header.border} inset`
  },
  menuButton: {
    marginLeft: -theme.spacing(1)
  },
  title: {
    color: customPalette.header.primaryText,
    fontWeight: 500,
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    lineHeight: '38px',
    letterSpacing: 0,
    [theme.breakpoints.down('sm')]: {
      ...theme.typography.h5,
      color: customPalette.header.primaryText
    }
  },
  primaryText: {
    color: customPalette.header.primaryText
  },
  secondaryText: {
    color: customPalette.header.secondaryText
  },
  selected: {
    color: theme.palette.primary.main
  },
  indicator: {
    backgroundColor: customPalette.header.primaryColor,
    height: theme.spacing(0.5)
  },
  tab: {
    padding: theme.spacing(1.25, 2),
    fontWeight: 500,
    fontSize: theme.typography.body2.fontSize,
    color: customPalette.header.secondaryText
  },
  overflow: {
    overflowX: 'hidden'
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
  invis: {
    display: 'none'
  }
}))

interface Props {
  auth: AuthUser,
  currentRoute: Route | undefined,
  onDrawerToggle: () => void,
  onNavigate: (url: string) => () => void,
  onRoutePreload: (url: string) => () => void,
  onRevokeAuth: () => void,
  onSettings: () => void
}

export const AuthNavigationHeader = memo(({
  auth,
  currentRoute,
  onDrawerToggle,
  onNavigate,
  onRevokeAuth,
  onRoutePreload,
  onSettings
}: Props) => {
  const { pathname } = useLocation()
  const classes = useStyles()

  const onNavigateTop = () => {
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const navigation = useMemo(() => {
    return currentRoute?.navigation
      ? currentRoute.navigation.filter(route => route.access.includes(auth.user.role))
      : null
  }, [currentRoute?.navigation, auth.user.role])

  const activeIndex = useMemo(() => {
    return navigation?.map(i => i.path).includes(pathname)
      ? navigation.map(i => i.path).indexOf(pathname)
      : null
  }, [navigation, pathname])

  return (
    <>
      <AppBar
        color='transparent'
        position='sticky'
        elevation={3}
        className={clsx(classes.primaryBar, classes.solidBar)}
      >
        <Toolbar>
          <Grid container spacing={2} alignItems='center'>
            <Hidden smUp>
              <Grid item>
                <IconButton
                  color='inherit'
                  aria-label='open drawer'
                  onClick={onDrawerToggle}
                  className={classes.menuButton}
                >
                  <Menu />
                </IconButton>
              </Grid>
            </Hidden>
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
            <Hidden smDown>
              <Grid item>
                <Tooltip title='Settings'>
                  <IconButton
                    color='inherit'
                    className={classes.primaryText}
                    onClick={onSettings}
                  >
                    <Cog />
                  </IconButton>
                </Tooltip>
              </Grid>
            </Hidden>
            <Grid item>
              <AuthLoggedInAction
                auth={auth}
                revokeAuth={onRevokeAuth}
                onSettings={onSettings}
              />
            </Grid>
          </Grid>
        </Toolbar>
      </AppBar>
      <div className={classes.overflow}>
        <AppBar
          component='div'
          color='transparent'
          position='static'
          elevation={0}
          className={clsx(classes.secondaryBar, classes.hideBar)}
        >
          <Toolbar>
            <Grid container alignItems='center' spacing={1}>
              <Grid item>
                <T
                  variant='h4'
                  component='h1'
                  className={classes.title}
                >
                  {currentRoute?.name}
                </T>
              </Grid>
              <Grid item xs />
            </Grid>
          </Toolbar>
        </AppBar>
        <AppBar
          component='div'
          color='transparent'
          position='static'
          elevation={0}
          className={clsx(classes.secondaryBar, classes.hideBar)}
        >
          <Toolbar className={classes.thirdToolBar}>
            {navigation && (
              <Tabs
                value={activeIndex}
                textColor='inherit'
                variant='scrollable'
                scrollButtons='off'
                classes={{ indicator: classes.indicator }}
              >
                <Tab value={null} className={classes.invis} />
                {navigation.map((route, index) => (
                  <Tab
                    key={route.path}
                    disableRipple
                    value={index}
                    textColor='inherit'
                    label={route.name}
                    onClick={onNavigate(route.path)}
                    onMouseEnter={onRoutePreload(route.path)}
                    classes={{
                      selected: classes.selected,
                      root: classes.tab
                    }}
                  />
                ))}
              </Tabs>
            )}
          </Toolbar>
        </AppBar>
      </div>
    </>
  )
})
