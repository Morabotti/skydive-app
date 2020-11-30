import React, { useCallback } from 'react'
import { customPalette } from '@theme'
import { Club } from '@types'
import { AuthRoles, ClubRole, ClubTab } from '@enums'
import { useAuth } from '@hooks'

import {
  AppBar,
  createStyles,
  Hidden,
  makeStyles,
  Tab,
  Tabs,
  Toolbar,
  Typography as T,
  useMediaQuery,
  useTheme
} from '@material-ui/core'

import {
  AccountMultiple,
  Cogs,
  Home,
  MapMarkerRadius
} from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  minHeight: {
    minHeight: 48
  },
  bar: {
    backgroundColor: customPalette.header.primaryColor,
    animation: `$startAnimationBig 500ms ${theme.transitions.easing.easeInOut}`,
    color: theme.palette.common.white,
    zIndex: theme.zIndex.appBar - 20,
    height: 48,
    marginTop: 64,
    [theme.breakpoints.down('xs')]: {
      marginTop: 54,
      animation: `$startAnimationSmall 500ms ${theme.transitions.easing.easeInOut}`
    }
  },
  '@keyframes startAnimationBig': {
    'from': { marginTop: -50, opacity: 0 },
    'to': { opacity: 1, marginTop: 64 }
  },
  '@keyframes startAnimationSmall': {
    'from': { marginTop: -50, opacity: 0 },
    'to': { opacity: 1, marginTop: 54 }
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: '1fr 5fr 1fr',
    alignItems: 'center',
    width: '100%',
    [theme.breakpoints.down('sm')]: {
      gridTemplateColumns: '5fr'
    }
  },
  name: {
    fontWeight: theme.typography.fontWeightBold
  },
  indicator: {
    backgroundColor: theme.palette.common.white
  },
  root: {
    fontWeight: theme.typography.fontWeightMedium
  }
}))

interface Props {
  loading: boolean,
  club?: Club,
  tab: ClubTab,
  onChangeTab: (set: ClubTab) => void,
  isOwner: boolean,
  isMember: boolean,
  accepted: boolean,
  clubRole?: ClubRole
}

export const ClubNavigation = ({
  loading,
  club,
  tab,
  onChangeTab,
  isOwner,
  isMember,
  accepted,
  clubRole
}: Props) => {
  const classes = useStyles()
  const theme = useTheme()
  const mobile = useMediaQuery(theme.breakpoints.down('xs'))
  const { auth } = useAuth()

  const handleChange = useCallback((e: React.ChangeEvent<{}>, value: ClubTab) => {
    onChangeTab(value)
  }, [onChangeTab])

  if (!club || loading) {
    return (
      <Toolbar className={classes.minHeight} />
    )
  }

  return (
    <>
      <AppBar
        color='transparent'
        position='fixed'
        elevation={1}
        className={classes.bar}
      >
        <Toolbar className={classes.minHeight}>
          <div className={classes.grid}>
            <Hidden smDown>
              <T variant='body1' className={classes.name}>{club.name}</T>
            </Hidden>
            <Tabs
              value={tab}
              onChange={handleChange}
              textColor='inherit'
              centered
              classes={{
                indicator: classes.indicator
              }}
            >
              <Tab
                className={classes.root}
                icon={mobile ? <Home /> : undefined}
                value={ClubTab.MAIN}
                label={!mobile ? 'Profile' : undefined}
              />
              <Tab
                className={classes.root}
                value={ClubTab.ACTIVITY}
                icon={mobile ? <MapMarkerRadius /> : undefined}
                disabled={!(isMember || auth?.user.role === AuthRoles.ADMIN) || !accepted}
                label={!mobile ? 'Activities' : undefined}
              />
              <Tab
                className={classes.root}
                value={ClubTab.USERS}
                icon={mobile ? <AccountMultiple /> : undefined}
                disabled={!(isMember || auth?.user.role === AuthRoles.ADMIN) || !accepted}
                label={!mobile ? 'Users' : undefined}
              />
              {(isOwner || auth?.user.role === AuthRoles.ADMIN) && (
                <Tab
                  className={classes.root}
                  value={ClubTab.CONFIG}
                  icon={mobile ? <Cogs /> : undefined}
                  label={!mobile ? 'Configuration' : undefined}
                />
              )}
            </Tabs>
            {clubRole && (
              <Hidden smDown>
                <T variant='body1' align='right' className={classes.name}>
                  {clubRole === ClubRole.CLUB ? 'OWNER' : accepted ? 'MEMBER' : 'PENDING'}
                </T>
              </Hidden>
            )}
          </div>
        </Toolbar>
      </AppBar>
      <Toolbar className={classes.minHeight} />
    </>
  )
}
