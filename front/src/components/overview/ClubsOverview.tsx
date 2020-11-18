import React from 'react'
import { AuthUser, Club } from '@types'

import {
  makeStyles,
  createStyles,
  Paper,
  Typography as T,
  Divider
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  paper: {
    width: '100%',
    height: '100%'
  },
  title: {
    padding: theme.spacing(0.5, 2)
  },
  wrapper: {
    padding: theme.spacing(2)
  }
}))

interface Props {
  auth: AuthUser | null,
  clubs: Club[] | null
}

export const ClubsOverview = ({
  auth,
  clubs
}: Props) => {
  const classes = useStyles()
  console.log(auth, clubs)

  return (
    <Paper square className={classes.paper}>
      <div className={classes.title}>
        <T variant='h4'>My Clubs</T>
      </div>
      <Divider />
      <div className={classes.wrapper}>
        <Paper square variant='outlined'>
          sadasdsasa
        </Paper>
      </div>
    </Paper>
  )
}
