import React from 'react'
import { DashboardContainer } from '@components/common'
import { ClubsOverview, ActivityOverview, JumpsOverview } from '@components/overview'
import { createStyles, Grid, makeStyles } from '@material-ui/core'
import { useOverview } from '@hooks'

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
  },
  paper: {
    width: '100%',
    height: '100%'
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gridGap: '16px',
    height: '100%',
    [theme.breakpoints.down('sm')]: {
      gridTemplateColumns: '1fr',
      gridGap: '8px'
    }
  }
}))

const OverviewView = () => {
  const classes = useStyles()
  const { clubs, activities } = useOverview()

  return (
    <DashboardContainer className={classes.container}>
      <div className={classes.grid}>
        <Grid container spacing={2} className={classes.container}>
          <Grid item xs={12} className={classes.item}>
            <ClubsOverview
              loading={clubs.isLoading}
              clubs={clubs.data}
            />
          </Grid>
          <Grid item xs={12} className={classes.item}>
            <JumpsOverview
              loading={clubs.isLoading}
            />
          </Grid>
        </Grid>
        <ActivityOverview
          loading={activities.isLoading}
          activities={activities.data}
        />
      </div>
    </DashboardContainer>
  )
}

export default OverviewView
