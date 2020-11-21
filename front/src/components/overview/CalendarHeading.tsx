import React from 'react'
import { Skeleton } from '@material-ui/lab'

import {
  makeStyles,
  createStyles,
  Typography as T
} from '@material-ui/core'

const useStyles = makeStyles(theme => createStyles({
  day: {
    padding: theme.spacing(0.5)
  },
  center: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    textAlign: 'center'
  },
  date: {
    fontWeight: theme.typography.fontWeightBold,
    marginBottom: theme.spacing(0.25)
  }
}))

interface Props {
  loading?: boolean,
  day: string
}

export const CalendarHeading = ({
  loading,
  day
}: Props) => {
  const classes = useStyles()

  if (loading) {
    <div className={classes.day}>
      <div className={classes.center}>
        <Skeleton width={30} height={21} variant='rect' />
      </div>
    </div>
  }

  return (
    <div className={classes.day}>
      <div className={classes.center}>
        <T
          variant='body1'
          className={classes.date}
          color='textSecondary'
        >{day}</T>
      </div>
    </div>
  )
}
