import React from 'react'
import { DashboardContainer } from '@components/common'
import { ClubsOverview } from '@components/overview'
import { createStyles, Grid, makeStyles, Paper } from '@material-ui/core'
import { useAuth, useOverview } from '@hooks'

const useStyles = makeStyles(() => createStyles({
  container: {
    height: '100%'
  },
  item: {
    width: '100%',
    height: '50%'
  },
  paper: {
    width: '100%',
    height: '100%'
  }
}))

const OverviewView = () => {
  const classes = useStyles()
  const { myClubs } = useOverview()
  const { auth } = useAuth()

  return (
    <DashboardContainer className={classes.container}>
      <Grid container spacing={2} className={classes.container}>
        <Grid item xs={12} sm={6} className={classes.item}>
          <Paper square className={classes.paper}>
            Test
          </Paper>
        </Grid>
        <Grid item xs={12} sm={6} className={classes.item}>
          <ClubsOverview
            auth={auth}
            clubs={myClubs}
          />
        </Grid>
        <Grid item xs={12} sm={6} className={classes.item}>
          <Paper square className={classes.paper}>
            Test
          </Paper>
        </Grid>
      </Grid>
    </DashboardContainer>
  )
}

export default OverviewView
