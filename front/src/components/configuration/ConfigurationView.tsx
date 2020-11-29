import React from 'react'
import { DashboardContainer, DashboardSection } from '@components/common'
import { createStyles, Grid, makeStyles, Paper } from '@material-ui/core'
import { ConfigurationLink } from '@components/configuration'
import { useApplicationNavigation, useConfiguration } from '@hooks'
import { AccountGroup, DatabaseCog } from 'mdi-material-ui'

const useStyles = makeStyles(theme => createStyles({
  container: {
    height: '100%'
  },
  item: {
    width: '100%',
    height: '50%',
    [theme.breakpoints.down('sm')]: {
      height: 'auto'
    }
  }
}))

const ConfigurationView = () => {
  const classes = useStyles()
  const { users, clubs } = useConfiguration()
  const { onNavigation, onRoutePreload } = useApplicationNavigation()

  return (
    <DashboardContainer className={classes.container}>
      <Grid container spacing={2} className={classes.container}>
        <Grid item xs={12} sm={6} className={classes.item}>
          <DashboardSection
            title='Manage Users'
            loading={users.isLoading}
            variant='no-padding'
          >
            <ConfigurationLink
              loading={users.isLoading}
              amount={users.data?.length}
              icon={AccountGroup}
              primaryText='Users In Platform'
              secondaryText='Open Users Management'
              onClick={onNavigation('/dashboard/configuration/users')}
              onMouseEnter={onRoutePreload('/dashboard/configuration/users')}
            />
          </DashboardSection>
        </Grid>
        <Grid item xs={12} sm={6} className={classes.item}>
          <DashboardSection
            title='Create New club'
            variant='no-padding'
            loading={clubs.isLoading}
          >
            <ConfigurationLink
              icon={DatabaseCog}
              loading={clubs.isLoading}
              amount={clubs.data?.length}
              primaryText='Clubs Created'
              secondaryText='Create New Club'
              onClick={onNavigation('/dashboard/configuration/club')}
              onMouseEnter={onRoutePreload('/dashboard/configuration/club')}
            />
          </DashboardSection>
        </Grid>
        <Grid item xs={12} sm={6} className={classes.item}>
          <Paper />
        </Grid>
        <Grid item xs={12} sm={6} className={classes.item}>
          <Paper />
        </Grid>
      </Grid>
    </DashboardContainer>
  )
}

export default ConfigurationView
